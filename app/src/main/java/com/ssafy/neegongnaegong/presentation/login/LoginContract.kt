package com.ssafy.neegongnaegong.presentation.login

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class LoginContract {
    sealed class Event : UiEvent {
        data class OnGoogleLoginSuccess(val idToken: String) : Event()
        data class OnGoogleLoginFailure(val exception: Throwable) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isFailure: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorSnackBar(val message: String) : Effect()
    }
}
