package com.ssafy.neegongnaegong.presentation.timer

import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() :
    BaseViewModel<TimerContract.Event, TimerContract.State, TimerContract.Effect>() {

    override fun createInitialState(): TimerContract.State {
        return TimerContract.State()
    }

    override fun handleEvent(event: TimerContract.Event) {
        when (event) {
            is TimerContract.Event.OnPauseClicked -> {
                setState {
                    copy(
                        isRunning = false,
                        isPauseDialogVisible = true
                    )
                }
            }
            is TimerContract.Event.OnDismissDialog -> {
                setState { copy(isPauseDialogVisible = false) }
            }
            is TimerContract.Event.OnAcceptDialog -> {
                setState {
                    copy(
                        isRunning = true,
                        isPauseDialogVisible = false
                    )
                }
            }
        }
    }

    private fun pauseTimer() {

    }

}
