package com.ssafy.neegongnaegong.presentation.timer

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class TimerContract {
    sealed class Event : UiEvent {
        data object OnPauseClicked : Event()
    }

    data class State(val timerState: TimerState) : UiState

    // todo retrofit 통신 연결 시 상태 수정
    sealed class TimerState {
        data object Idle : TimerState()
        data object Loading : TimerState()
        data object Success : TimerState()
    }

    sealed class Effect : UiEffect {

    }
}