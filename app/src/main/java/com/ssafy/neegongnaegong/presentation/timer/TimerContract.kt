package com.ssafy.neegongnaegong.presentation.timer

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class TimerContract {
    sealed class Event : UiEvent {
        data object OnPauseClicked : Event()
        data object OnDismissDialog : Event()
        data object OnAcceptDialog : Event()
    }

    data class State(
        val isRunning: Boolean = true,
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isFailure: Boolean = false,
        val isPauseDialogVisible: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect {
        data object ShowPauseDialog : Effect()
    }
}
