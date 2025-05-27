package com.ssafy.neegongnaegong.presentation.notification

import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiModel

interface NotificationContract {
    sealed class Event : UiEvent {
        data object RefreshEvent : Event()
        data object DeleteAllNotification : Event()
        data class DeleteNotification(val data: NotificationUiModel) : Event()
        data class MoveNotification(val data: NotificationUiModel) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object ScrollToFirstPosition : Effect()
    }

    sealed class Error : ErrorContext {
        data object ShowErrorMessage : Error()
    }
}
