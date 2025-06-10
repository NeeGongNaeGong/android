package com.ssafy.neegongnaegong.presentation.group.user.search

import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserUiModel

class UserSearchContract {
    sealed class Event : UiEvent {
        data class OnTypingSearch(
            val keyword: String,
        ) : Event()

        data class OnDeclareClick(
            val user: UserUiModel,
        ) : Event()
    }

    data class State(
        // api
        val isLoading: Boolean = false,
        // search
        val searchKeyword: String = "",
    ) : UiState

    sealed class Effect : UiEffect

    sealed class Error : ErrorContext {
        data object GetUserListError : Error()
    }
}
