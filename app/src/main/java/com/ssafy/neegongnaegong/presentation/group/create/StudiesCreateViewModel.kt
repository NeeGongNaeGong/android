package com.ssafy.neegongnaegong.presentation.group.create

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import com.ssafy.neegongnaegong.domain.usecase.category.GetCategoriesUseCase
import com.ssafy.neegongnaegong.domain.usecase.category.GetTagsUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.CreateStudiesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class StudiesCreateViewModel
    @Inject
    constructor(
        private val createStudiesUseCase: CreateStudiesUseCase,
        private val getCategoriesUseCase: GetCategoriesUseCase,
        private val getTagsUseCase: GetTagsUseCase,
    ) : BaseViewModel<StudiesCreateContract.Event, StudiesCreateContract.State, StudiesCreateContract.Effect>() {
        override fun createInitialState(): StudiesCreateContract.State = StudiesCreateContract.State()

        override fun handleEvent(event: StudiesCreateContract.Event) {
            when (event) {
                is StudiesCreateContract.Event.OnLoad -> onLoad()
                is StudiesCreateContract.Event.OnNameChanged -> {
                    setStudyInfo(name = event.name)
                    validateCreateStudies()
                }

                is StudiesCreateContract.Event.OnIsPublicChanged -> setStudyInfo(isPublic = event.isPublic)
                is StudiesCreateContract.Event.OnTargetStudyTimeChanged ->
                    setStudyInfo(targetStudyTime = event.targetStudyTime)

                is StudiesCreateContract.Event.OnMaxMembersChanged -> setStudyInfo(maxMembers = event.maxMembers)
                is StudiesCreateContract.Event.OnSelectedCategory -> {
                    selectedCategory(event.category)
                    validateCreateStudies()
                }

                is StudiesCreateContract.Event.OnTagSelected -> {
                    selectedTag(event.tag)
                    validateCreateStudies()
                }

                is StudiesCreateContract.Event.OnTagUnSelected -> {
                    unSelectedTag(event.tag)
                    validateCreateStudies()
                }

                is StudiesCreateContract.Event.OnDescriptionChanged -> {
                    setStudyInfo(description = event.description)
                    validateCreateStudies()
                }

                is StudiesCreateContract.Event.OnProfileImgChanged -> setStudyInfo(profileImg = event.profileImg)
                is StudiesCreateContract.Event.OnSelectedImage ->
                    setImageExtension(
                        event.extension,
                    )

                is StudiesCreateContract.Event.OnSelectedImageRequest -> setImageRequest(event.requestImage)
                is StudiesCreateContract.Event.OnCreateStudiesClicked -> createStudies()
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

        private fun setImageExtension(extension: String?) {
            if (extension == null) return
            setState {
                copy(imageExtension = extension)
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

        private fun validateCreateStudies() {
            if (uiState.value.studyInfo.name
                    .isBlank() ||
                uiState.value.studyInfo.description
                    .isBlank() ||
                uiState.value.selectedCategory == null ||
                uiState.value.selectedTags.isEmpty()
            ) {
                setState { copy(validateCreateStudies = false) }
            } else {
                setState { copy(validateCreateStudies = true) }
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
                        imageRequestBody = requestImage,
                        imageExtension = imageExtension,
                    ).withLoading {
                        setState { copy(isOnCreate = it) }
                    }.safeCollect {
                        setEffect { StudiesCreateContract.Effect.NavigateToMyStudies }
                    }
                }
            }
    }
