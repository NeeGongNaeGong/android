package com.ssafy.neegongnaegong.presentation.group

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.GetStudiesUseCase
import com.ssafy.neegongnaegong.domain.usecase.studies.GetStudiesListUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

private const val TAG = "StudiesViewModel"

@HiltViewModel
class StudiesViewModel
    @Inject
    constructor(
        private val getStudiesUseCase: GetStudiesUseCase,
        private val getStudiesListUseCase: GetStudiesListUseCase,
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
            }
        }

        override fun createInitialState(): StudiesContract.State = StudiesContract.State()

        override fun handleEvent(event: StudiesContract.Event) {
            when (event) {
                is StudiesContract.Event.OnLoadStudies -> loadStudies()
                is StudiesContract.Event.StudiesClicked -> {
                    setEffect { StudiesContract.Effect.NavigateToGroupDetail(event.studiesId) }
                }
            }
        }

        private fun loadStudies() {
            if (uiState.value.hasNext.not() || uiState.value.isLoading) { // 더 이상 불러올 데이터가 없으면 리턴
                return
            }

            viewModelScope.launch {
                // TODO : 로직 처리 필요
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                getStudiesListUseCase(
                    cursorCreatedAt =
                        uiState.value.cursorCreateAt?.let {
                            runCatching {
                                // 밀리초를 3자리 이하로 잘라냄
                                val fixedDate =
                                    if (it.contains('.')) {
                                        val split = it.split('.')
                                        val milli = split[1].take(3) // 밀리초만 가져와 잘라냄
                                        "${split[0]}.$milli"
                                    } else {
                                        it
                                    }
                                LocalDateTime.parse(fixedDate, formatter)
                            }.onFailure {
                                Log.e(TAG, "Failed to parse date: $it", it)
                            }.getOrNull()
                        },
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
    }
