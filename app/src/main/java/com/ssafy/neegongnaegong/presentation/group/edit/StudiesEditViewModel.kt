package com.ssafy.neegongnaegong.presentation.group.edit

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.file.UploadPathType
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import com.ssafy.neegongnaegong.domain.usecase.category.GetCategoriesUseCase
import com.ssafy.neegongnaegong.domain.usecase.category.GetTagsUseCase
import com.ssafy.neegongnaegong.domain.usecase.file.IssuePresignedUrlUseCase
import com.ssafy.neegongnaegong.domain.usecase.s3.UploadImageToS3UseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.UpdateStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetStudyGroupDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class StudiesEditViewModel
    @Inject
    constructor(
        private val getStudyGroupDetailUseCase: GetStudyGroupDetailUseCase,
        private val updateStudiesUseCase: UpdateStudiesUseCase,
        private val getCategoriesUseCase: GetCategoriesUseCase,
        private val getTagsUseCase: GetTagsUseCase,
        private val issuePresignedUrlUseCase: IssuePresignedUrlUseCase,
        private val uploadImageToS3UseCase: UploadImageToS3UseCase,
    ) : BaseViewModel<StudiesEditContract.Event, StudiesEditContract.State, StudiesEditContract.Effect>() {
        override fun createInitialState(): StudiesEditContract.State = StudiesEditContract.State(studyGroupId = -1)

        override fun handleEvent(event: StudiesEditContract.Event) {
            when (event) {
                is StudiesEditContract.Event.OnLoad -> {
                    onLoad(event.studyGroupId)
                }

                is StudiesEditContract.Event.OnNameChanged -> {
                    setStudyInfo(name = event.name)
                    validateUpdateStudies()
                }

                is StudiesEditContract.Event.OnIsPublicChanged -> setStudyInfo(isPublic = event.isPublic)
                is StudiesEditContract.Event.OnTargetStudyTimeChanged ->
                    setStudyInfo(targetStudyTime = event.targetStudyTime)

                is StudiesEditContract.Event.OnMaxMembersChanged -> setStudyInfo(maxMembers = event.maxMembers)
                is StudiesEditContract.Event.OnSelectedCategory -> {
                    selectedCategory(event.category)
                    validateUpdateStudies()
                }

                is StudiesEditContract.Event.OnTagSelected -> {
                    selectedTag(event.tag)
                    validateUpdateStudies()
                }

                is StudiesEditContract.Event.OnTagUnSelected -> {
                    unSelectedTag(event.tag)
                    validateUpdateStudies()
                }

                is StudiesEditContract.Event.OnDescriptionChanged -> {
                    setStudyInfo(description = event.description)
                    validateUpdateStudies()
                }

                is StudiesEditContract.Event.OnClearProfileImg -> {
                    setStudyInfo(profileImg = null)
                }

                is StudiesEditContract.Event.OnProfileImgChanged -> setStudyInfo(profileImg = event.profileImg)
                is StudiesEditContract.Event.OnSelectedImage ->
                    issuePresignedUrl(
                        event.extension,
                    )

                is StudiesEditContract.Event.OnSelectedImageRequest -> setImageRequest(event.requestImage)
                is StudiesEditContract.Event.OnCreateStudiesClicked -> updateStudies()
            }
        }

        private fun onLoad(studyGroupId: Long) {
            setState { copy(studyGroupId = studyGroupId) }
            viewModelScope.launch {
                getStudyGroupDetailUseCase(studyGroupId)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState {
                            copy(
                                studyInfo =
                                    StudyInfo(
                                        result.name,
                                        result.maxMembers,
                                        result.description,
                                        result.profileImg,
                                        result.isPublic,
                                        result.targetStudyTime,
                                        Category(result.category.id, result.category.name),
                                        result.tags.map { Tag(it.id, it.name) },
                                    ),
                            )
                        }
                        setState { copy(currentMembers = result.currentMembers) }
                        setState { copy(selectedCategory = Category(result.category.id, result.category.name)) }
                        setState { copy(selectedTags = result.tags.map { Tag(it.id, it.name) }) }
                        selectedCategory(Category(result.category.id, result.category.name))
                    }
            }

            viewModelScope.launch {
                getCategoriesUseCase()
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState { copy(categories = result) }
                    }
            }
        }

        private fun issuePresignedUrl(extension: String?) {
            if (extension == null) {
                return
            }
            viewModelScope.launch {
                issuePresignedUrlUseCase(
                    uploadPathType = UploadPathType.PROFILE_STUDY_TEMP.path,
                    imageExtension = extension,
                ).withLoading {
                }.safeCollect { result ->
                    setState { copy(presignedUrl = result.presignedUrl) }
                    setStudyInfo(profileImg = result.imageUrl)
                    val currentState = uiState.value
                    if (currentState.presignedUrl == null || currentState.requestImage == null) return@safeCollect
                    uploadImageToS3UseCase(
                        presignedUrl = currentState.presignedUrl,
                        request = currentState.requestImage,
                    )
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

        private fun validateUpdateStudies() {
            if (uiState.value.studyInfo.name
                    .isBlank()
            ) {
                setState { copy(validateCreateStudies = false) }
                return
            }
            if (uiState.value.studyInfo.description
                    .isBlank()
            ) {
                setState { copy(validateCreateStudies = false) }
                return
            }
            if (uiState.value.selectedCategory == null) {
                setState { copy(validateCreateStudies = false) }
                return
            }
            if (uiState.value.selectedTags.isEmpty()) {
                setState { copy(validateCreateStudies = false) }
                return
            }
            setState { copy(validateCreateStudies = true) }
        }

        private fun updateStudies() =
            viewModelScope.launch {
                setStudyInfo(
                    category = uiState.value.selectedCategory,
                    tags = uiState.value.selectedTags,
                )
                with(uiState.value) {
                    updateStudiesUseCase(
                        studyGroupId = uiState.value.studyGroupId,
                        studyInfo = studyInfo,
                    ).withLoading {
                        setState { copy(isOnUpdate = it) }
                    }.safeCollect {
                        setEffect { StudiesEditContract.Effect.NavigateToBack }
                    }
                }
            }
    }
