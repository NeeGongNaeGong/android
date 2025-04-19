package com.ssafy.neegongnaegong.presentation.login

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.auth.LoginUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.util.AuthDestinationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
            is LoginContract.Event.OnGoogleLoginFailure -> {
                setEffect {
                    LoginContract.Effect.ShowErrorSnackBar(event.exception.message ?: "에러 발생")
                }
            }
        }
    }

    private fun login(idToken: String) = viewModelScope.launch {
        loginUseCase(idToken).safeCollect {
            AuthDestinationManager.valid()
        }
    }

    private suspend fun <T> Flow<T>.safeCollect(block: suspend (T) -> Unit = {}) {
        runCatching {
            collect { value -> block(value) }
        }.onFailure { exception ->
            exception.printStackTrace()
            AuthDestinationManager.invalid()
            setEffect {
                LoginContract.Effect.ShowErrorSnackBar(exception.message ?: "에러 발생")
            }
        }
    }
}
