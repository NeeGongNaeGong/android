package com.ssafy.neegongnaegong.presentation.group.management

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.file.UploadPathType
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import com.ssafy.neegongnaegong.domain.usecase.category.GetCategoriesUseCase
import com.ssafy.neegongnaegong.domain.usecase.category.GetTagsUseCase
import com.ssafy.neegongnaegong.domain.usecase.file.IssuePresignedUrlUseCase
import com.ssafy.neegongnaegong.domain.usecase.s3.UploadImageToS3UseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.CreateStudiesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

private const val TAG = "StudiesManagementViewModel"

@HiltViewModel
class StudiesManagementViewModel
    @Inject
    constructor(
        private val createStudiesUseCase: CreateStudiesUseCase,
        private val getCategoriesUseCase: GetCategoriesUseCase,
        private val getTagsUseCase: GetTagsUseCase,
        private val issuePresignedUrlUseCase: IssuePresignedUrlUseCase,
        private val uploadImageToS3UseCase: UploadImageToS3UseCase,
    ) : BaseViewModel<StudiesManagementContract.Event, StudiesManagementContract.State, StudiesManagementContract.Effect>() {
        override fun createInitialState(): StudiesManagementContract.State = StudiesManagementContract.State()

        override fun handleEvent(event: StudiesManagementContract.Event) {
            when (event) {
                is StudiesManagementContract.Event.OnLoad -> onLoad()
                is StudiesManagementContract.Event.OnNameChanged -> setStudyInfo(name = event.name)
                is StudiesManagementContract.Event.OnIsPublicChanged -> setStudyInfo(isPublic = event.isPublic)
                is StudiesManagementContract.Event.OnTargetStudyTimeChanged ->
                    setStudyInfo(
                        targetStudyTime = event.targetStudyTime,
                    )

                is StudiesManagementContract.Event.OnMaxMembersChanged -> setStudyInfo(maxMembers = event.maxMembers)
                is StudiesManagementContract.Event.OnSelectedCategory -> selectedCategory(event.category)
                is StudiesManagementContract.Event.OnTagSelected -> selectedTag(event.tag)
                is StudiesManagementContract.Event.OnTagUnSelected -> unSelectedTag(event.tag)
                is StudiesManagementContract.Event.OnDescriptionChanged -> setStudyInfo(description = event.description)
                is StudiesManagementContract.Event.OnProfileImgChanged -> setStudyInfo(profileImg = event.profileImg)
                is StudiesManagementContract.Event.OnSelectedImage ->
                    issuePresignedUrl(
                        event.imageUri,
                        event.extension,
                    )

                is StudiesManagementContract.Event.OnSelectedImageRequest -> setImageRequest(event.requestImage)
                is StudiesManagementContract.Event.OnCreateStudiesClicked -> createStudies()
            }
        }

        private fun onLoad() {
            viewModelScope.launch {
                getCategoriesUseCase()
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState { copy(categories = result) }
                    }
            }
        }

        private fun issuePresignedUrl(
            imageUri: Uri,
            extension: String?,
        ) {
            if (extension == null) {
                return
            }
            viewModelScope.launch {
                Log.d(TAG, "issuePresignedUrl: $imageUri")
                issuePresignedUrlUseCase(
                    uploadPathType = UploadPathType.PROFILE_STUDY_TEMP.path,
                    imageExtension = extension,
                ).withLoading {
                    Log.d(TAG, "issuePresignedUrl: $it")
                }.safeCollect { result ->
                    Log.d(TAG, "issuePresignedUrl: $result")
                    setState { copy(presignedUrl = result.presignedUrl) }
                    setStudyInfo(profileImg = result.imageUrl)
                    if (uiState.value.presignedUrl != null && uiState.value.requestImage != null) {
                        uploadImageToS3UseCase(
                            presignedUrl = uiState.value.presignedUrl!!,
                            request = uiState.value.requestImage!!,
                        )
                    }
                }
            }
        }

        private fun setImageRequest(request: RequestBody?) {
            setState {
                copy(requestImage = request)
            }
        }

        private fun selectedCategory(category: Category) {
            viewModelScope.launch {
                setState { copy(selectedCategory = category) }
                getTagsUseCase(category.id)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState { copy(tags = result) }
                    }
            }
        }

        private fun selectedTag(tag: Tag) {
            setState {
                copy(
                    selectedTags = uiState.value.selectedTags + tag,
                )
            }
        }

        private fun unSelectedTag(tag: Tag) {
            setState {
                copy(
                    selectedTags = uiState.value.selectedTags - tag,
                )
            }
        }

        private fun setStudyInfo(
            name: String = uiState.value.studyInfo.name,
            maxMembers: Int = uiState.value.studyInfo.maxMembers,
            description: String = uiState.value.studyInfo.description,
            profileImg: String? = uiState.value.studyInfo.profileImg,
            isPublic: Boolean = uiState.value.studyInfo.isPublic,
            targetStudyTime: Int = uiState.value.studyInfo.targetStudyTime,
            category: Category? = uiState.value.studyInfo.category,
            tags: List<Tag> = uiState.value.studyInfo.tags,
        ) {
            setState {
                copy(
                    studyInfo =
                        StudyInfo(
                            name = name,
                            maxMembers = maxMembers,
                            description = description,
                            profileImg = profileImg,
                            isPublic = isPublic,
                            targetStudyTime = targetStudyTime,
                            category = category,
                            tags = tags,
                        ),
                )
            }
        }

        private fun createStudies() =
            viewModelScope.launch {
                setStudyInfo(
                    category = uiState.value.selectedCategory,
                    tags = uiState.value.selectedTags,
                )
                with(uiState.value) {
                    createStudiesUseCase(
                        studyInfo = studyInfo,
                    ).withLoading {
                        setState { copy(isOnCreate = it) }
                    }.safeCollect {
                        setEffect { StudiesManagementContract.Effect.NavigateToBack }
                    }
                }
            }
    }
