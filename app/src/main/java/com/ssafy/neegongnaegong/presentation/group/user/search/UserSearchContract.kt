package com.ssafy.neegongnaegong.presentation.group.user.search

import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserReportData
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserUiModel

class UserSearchContract {
    sealed interface Event : UiEvent {
        data class OnTypingSearch(
            val keyword: String,
        ) : Event

        data class OnReportClick(
            val user: UserUiModel,
        ) : Event

        data object OnReportDialogDismiss : Event

        data class OnReportDialogConfirm(
            val userReportData: UserReportData,
        ) : Event

        data object OnBackClick : Event
    }

    data class State(
        // api
        val isLoading: Boolean = false,
        // search
        val searchKeyword: String = "",
        // dialog
        val isReportDialogOpen: Boolean = false,
        val reportUser: UserUiModel = UserUiModel.toDefault(),
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateBack : Effect
    }

    sealed interface Error : ErrorContext {
        data object GetUserListError : Error
    }
}
