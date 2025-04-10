package com.ssafy.neegongnaegong.presentation.login

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.auth.LoginUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {
    override fun createInitialState(): LoginContract.State = LoginContract.State()

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnGoogleLoginSuccess -> login(event.tokenId)
            is LoginContract.Event.OnGoogleLoginFailure -> {}
        }
    }

    private fun login(tokenId: String) = viewModelScope.launch {
        loginUseCase(tokenId, "")
    }
}
