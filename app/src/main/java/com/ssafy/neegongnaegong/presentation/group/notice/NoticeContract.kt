package com.ssafy.neegongnaegong.presentation.group.notice

import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class NoticeContract {
    sealed interface Event : UiEvent {
        data object OnClickCompleteButton : Event

        data class OnChangeTitle(val title: String) : Event

        data class OnChangeContent(val content: String) : Event
    }

    data class State(val title: String, val content: String) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToBackStack : Effect()
    }

    sealed interface Error : ErrorContext {
        data object CreateNoticeError : Error
    }
}
