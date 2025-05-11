package com.ssafy.neegongnaegong.presentation.timer

import android.os.SystemClock
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.learningrecord.CreateLearningRecordUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class TimerViewModel
    @Inject
    constructor(
        private val createLearningRecordUseCase: CreateLearningRecordUseCase,
    ) : BaseViewModel<TimerContract.Event, TimerContract.State, TimerContract.Effect>() {
        private var timerJob: Job? = null

        override fun createInitialState(): TimerContract.State = TimerContract.State()

        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            val error = errorContext as? TimerContract.Error ?: return

            when (error) {
                is TimerContract.Error.CreateLearningRecordError ->
                    showErrorMessage(
                        message = "공부 기록을 등록 하지 못했습니다.",
                        action = SnackbarManager.Action.retry { retry() },
                    )
            }
        }

        override fun handleEvent(event: TimerContract.Event) {
            when (event) {
                is TimerContract.Event.OnFlip -> {
                    if (event.isBack) {
                        setState { copy(isDialogShow = false) }
                        if (!uiState.value.isRunning) {
                            startTimer()
                        }
                    } else {
                        if (!uiState.value.isDialogShow) {
                            setState { copy(isPauseDialogVisible = true, isDialogShow = true) }
                        }
                    }
                }

                // Play Pause Button
                is TimerContract.Event.OnPauseClicked -> {
                    setState { copy(isPauseDialogVisible = true) }
                }

                is TimerContract.Event.OnPlayClicked -> {
                    if (!uiState.value.isRunning) {
                        startTimer()
                    }
                }

                // Pause Dialog
                is TimerContract.Event.OnCancelDialog -> {
                    setState {
                        copy(isPauseDialogVisible = false)
                    }
                }

                is TimerContract.Event.OnDismissDialog -> {
                    val currentElapsedTime = SystemClock.elapsedRealtime() - uiState.value.startTime
                    setState {
                        copy(
                            isRunning = false,
                            isPauseDialogVisible = false,
                            totalElapsedTime = totalElapsedTime + currentElapsedTime,
                        )
                    }
                    timerJob?.cancel()
                }

                is TimerContract.Event.OnConfirmDialog -> {
                    val currentElapsedTime = SystemClock.elapsedRealtime() - uiState.value.startTime
                    val totalTime = uiState.value.totalElapsedTime + currentElapsedTime

                    setState { copy(learningRecord = learningRecord.copy(endAt = LocalDateTime.now())) }
                    createLearningRecord(totalTime = totalTime)
                }

                // Learning Cancel Dialog
                is TimerContract.Event.OnLearningCancelDialogConfirm -> {
                    setEffect { TimerContract.Effect.CloseTimerActivity }
                }

                is TimerContract.Event.OnLearningCancelDialogDismiss -> {
                    setState { copy(isLearningCancelDialogShow = false) }
                }

                is TimerContract.Event.OnLearningCancelDialogShow -> {
                    setState { copy(isLearningCancelDialogShow = true) }
                }

                // Screen On Off
                // 화면이 꺼졌을 때, 지금은 별다른 로직이 필요 없음
                is TimerContract.Event.OffScreen -> {
                }

                is TimerContract.Event.OnScreen -> {
                }
            }
        }

        // api
        private fun createLearningRecord(totalTime: Long) =
            viewModelScope.launch {
                createLearningRecordUseCase(
                    uiState.value.learningRecord,
                ).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect(TimerContract.Error.CreateLearningRecordError) { result ->
                    setState {
                        copy(
                            isTimerScreen = false,
                            isRunning = false,
                            isPauseDialogVisible = false,
                            totalElapsedTime = totalTime,
                            learningRecord = learningRecord.copy(id = result),
                        )
                    }
                    timerJob?.cancel()
                }
            }

        // timer
        private fun startTimer() {
            timerJob?.cancel()
            setState {
                copy(
                    startTime = SystemClock.elapsedRealtime(),
                    isRunning = true,
                    learningRecord = if (isFirstTimer) learningRecord.copy(startAt = LocalDateTime.now()) else learningRecord,
                    isFirstTimer = false,
                )
            }
            timerJob =
                viewModelScope.launch {
                    while (uiState.value.isRunning) {
                        val currentElapsedTime = SystemClock.elapsedRealtime() - uiState.value.startTime
                        setState {
                            copy(
                                startTime = SystemClock.elapsedRealtime(),
                                totalElapsedTime = totalElapsedTime + currentElapsedTime,
                            )
                        }
                        delay(1000L)
                    }
                }
        }
    }
