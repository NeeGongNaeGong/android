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
        data class AcceptGroupJoinRequest(val data: NotificationUiModel) : Event()
        data class RejectGroupJoinRequest(val data: NotificationUiModel) : Event()
    }

    data class State(
        val isLoading: Boolean = true,
        val isModifying: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object ShowInvalidGroupIdErrorMessage : Effect()
        data object ScrollToFirstPosition : Effect()
        data class NavigateToGroup(val groupId: Long) : Effect()
        data class NavigateToNotice(val groupId: Long, val noticeId: Long) : Effect()
        data class NavigateToVote(val groupId: Long, val voteId: Long) : Effect()
    }

    sealed class Error : ErrorContext {
        data object ShowErrorMessage : Error()
    }
}
