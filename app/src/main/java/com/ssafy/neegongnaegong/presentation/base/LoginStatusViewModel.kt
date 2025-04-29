package com.ssafy.neegongnaegong.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.presentation.util.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginStatusViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {
    data class State(val isLoading: Boolean, val isLoginSuccess: Boolean)

    private val isLoading = MutableStateFlow(true)

    /**
     * loading이 되었는지와 login이 성공했는지를 관리하는 상태
     */
    val state = combine(isLoading, authManager.isValid) { isLoading, isValid ->
        State(isLoading = isLoading, isLoginSuccess = isValid)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        State(isLoading = true, isLoginSuccess = false)
    )

    init {
        viewModelScope.launch {
            authManager.tryAuth { isLoading.value = it }
        }
    }
}
