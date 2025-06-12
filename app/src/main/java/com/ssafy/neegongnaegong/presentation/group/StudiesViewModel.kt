package com.ssafy.neegongnaegong.presentation.group

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.studies.ApplyStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.CancelApplicationsStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesListUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "StudiesViewModel"

@HiltViewModel
class StudiesViewModel
    @Inject
    constructor(
        private val getStudiesListUseCase: GetStudiesListUseCase,
        private val applyStudiesUseCase: ApplyStudiesUseCase,
        private val cancelApplyStudiesUseCase: CancelApplicationsStudiesUseCase,
    ) : BaseViewModel<StudiesContract.Event, StudiesContract.State, StudiesContract.Effect>() {
        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            val error = errorContext as? StudiesContract.Error ?: return
            when (error) {
                StudiesContract.Error.GetStudiesListError -> {
                    Log.d(TAG, "handleException: GetStudiesListError")
                    showErrorMessage(
                        "스터디 목록을 가져오지 못했습니다.",
                        SnackbarManager.Action.retry { retry() },
                    )
                }

                is StudiesContract.Error.ApplyStudiesError -> { // TODO : 가입된 스터디, 가입 신청한 스터디 구분 필요 (현재 가입 신청한)
                    showErrorMessage(
                        "이미 신청된 스터디입니다.",
                        SnackbarManager.Action(label = "신청철회") { cancelApplyStudies(error.studyGroupId) },
                    )
                }
            }
        }

        override fun createInitialState(): StudiesContract.State = StudiesContract.State()

        override fun handleEvent(event: StudiesContract.Event) {
            when (event) {
                is StudiesContract.Event.OnLoadStudies -> loadStudies()
                is StudiesContract.Event.StudiesClicked -> {
                    setEffect { StudiesContract.Effect.NavigateToGroupDetail(event.studiesId) }
                }

                is StudiesContract.Event.OnStudiesApplyClicked -> {
                    applyStudies(event.studiesId)
                }
            }
        }

        private fun loadStudies() {
            if (uiState.value.hasNext.not() || uiState.value.isLoading) { // 더 이상 불러올 데이터가 없으면 리턴
                return
            }

            viewModelScope.launch {
                getStudiesListUseCase(
                    cursorCreatedAt = uiState.value.cursorCreateAt,
                    cursorId = uiState.value.cursorId,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect(StudiesContract.Error.GetStudiesListError) { result ->
                    setState {
                        copy(
                            studiesList = studiesList + result.content,
                        )
                    }
                    setState {
                        copy(
                            hasNext = result.hasNext,
                            cursorCreateAt = result.cursorCreatedAt,
                            cursorId = result.cursorId,
                        )
                    }
                }
            }
        }

        private fun applyStudies(studyGroupId: Long) {
            viewModelScope.launch {
                applyStudiesUseCase(studyGroupId)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect(StudiesContract.Error.ApplyStudiesError(studyGroupId)) { result ->
                        showMessage("가입 신청이 완료되었습니다.")
                    }
            }
        }

        private fun cancelApplyStudies(studyGroupId: Long) {
            viewModelScope.launch {
                cancelApplyStudiesUseCase(studyGroupId)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        showMessage("가입 신청이 철회되었습니다.")
                    }
            }
        }
    }
