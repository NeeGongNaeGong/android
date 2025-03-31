package com.ssafy.neegongnaegong.presentation.timer

import android.os.SystemClock
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() :
    BaseViewModel<TimerContract.Event, TimerContract.State, TimerContract.Effect>() {

    private var timerJob: Job? = null

    override fun createInitialState(): TimerContract.State {
        return TimerContract.State()
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

            // Dialog
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
                        totalElapsedTime = totalElapsedTime + currentElapsedTime
                    )
                }
                timerJob?.cancel()
            }

            is TimerContract.Event.OnAcceptDialog -> {
                setState {
                    copy(
                        isRunning = true,
                        isPauseDialogVisible = false,
                        startTime = SystemClock.elapsedRealtime()
                    )
                }
                startTimer()
            }
            // Screen On Off
            // 화면이 꺼졌을 때, 지금은 별다른 로직이 필요 없음
            is TimerContract.Event.OffScreen -> {
            }


            is TimerContract.Event.OnScreen -> {

            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        setState {
            copy(
                startTime = SystemClock.elapsedRealtime(),
                isRunning = true
            )
        }
        timerJob = viewModelScope.launch {
            while (uiState.value.isRunning) {
                val currentElapsedTime = SystemClock.elapsedRealtime() - uiState.value.startTime
                setState {
                    copy(
                        startTime = SystemClock.elapsedRealtime(),
                        totalElapsedTime = totalElapsedTime + currentElapsedTime
                    )
                }
                delay(1000L)
            }
        }
    }

    private fun pauseTimer() {

    }

}
