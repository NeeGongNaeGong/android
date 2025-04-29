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
            is LoginContract.Event.OnGoogleLoginSuccess -> login(event.idToken)
            is LoginContract.Event.OnGoogleLoginFailure -> showErrorMessage(event.exception.message ?: "에러 발생")
        }
    }

    private fun login(idToken: String) = viewModelScope.launch {
        loginUseCase(idToken).safeCollect {
            // 로그인 성공했으면 MainScreen으로 Navigate하는 이벤트 emit
            setEffect { LoginContract.Effect.NavigateToMainScreen }
        }
    }
}
