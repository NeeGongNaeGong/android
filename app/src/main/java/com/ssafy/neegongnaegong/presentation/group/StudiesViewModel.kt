package com.ssafy.neegongnaegong.presentation.group

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.GetStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.ApplyStudiesUseCase
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
        private val getStudiesUseCase: GetStudiesUseCase,
        private val getStudiesListUseCase: GetStudiesListUseCase,
        private val applyStudiesUseCase: ApplyStudiesUseCase,
    ) : BaseViewModel<StudiesContract.Event, StudiesContract.State, StudiesContract.Effect>() {
        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            val error = errorContext as? StudiesContract.Error ?: return
            when (error) {
                StudiesContract.Error.GetStudiesListError -> {
                    // TODO : snackbarHost = { NeeGongNaeGongSnackbarHost() }, 커밋 붙으면 스낵바 나옴
                    Log.d(TAG, "handleException: GetStudiesListError")
                    showErrorMessage(
                        "스터디 목록을 가져오지 못했습니다.",
                        SnackbarManager.Action.retry { retry() },
                    )
                }

                StudiesContract.Error.ApplyStudiesError -> {
                    showErrorMessage(
                        "이미 신청된",
                        SnackbarManager.Action.retry { retry() },
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

        private fun applyStudies(studiesId: Long) {
            viewModelScope.launch {
                applyStudiesUseCase(studiesId)
                    .withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect(StudiesContract.Error.ApplyStudiesError) { result ->
                    }
            }
        }
    }
