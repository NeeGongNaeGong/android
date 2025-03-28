package com.ssafy.neegongnaegong.presentation.timer

import android.os.SystemClock
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class TimerContract {
    sealed class Event : UiEvent {
        data object OnPauseClicked : Event()
        data object OnPlayClicked : Event()
        data object OnDismissDialog : Event()
        data object OnAcceptDialog : Event()
        data object OnCancelDialog : Event()
        data object OffScreen : Event()
        data object OnScreen : Event()
        data class OnFlip(val isBack: Boolean) : Event()
    }

    data class State(
        val isRunning: Boolean = false,
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isFailure: Boolean = false,
        val isPauseDialogVisible: Boolean = false,
        val startTime: Long = SystemClock.elapsedRealtime(),
        val totalElapsedTime: Long = 0L,
        val isDialogShow: Boolean = true,
    ) : UiState

    sealed class Effect : UiEffect {
        data object ShowPauseDialog : Effect()
    }
}
