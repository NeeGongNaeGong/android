package com.ssafy.neegongnaegong.presentation.group.user.search

import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class UserSearchContract {
    sealed class Event : UiEvent {
        data class OnClickSearch(
            val keyword: String,
        ) : Event()

        data class OnTypingSearch(
            val keyword: String,
        ) : Event()
    }

    data class State(
        // api
        val isLoading: Boolean = false,
        // user
        val userList: List<User> = emptyList(),
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToStudiesScreen : Effect()
    }

    sealed class Error : ErrorContext {
        data object GetUserListError : Error()
    }
}
