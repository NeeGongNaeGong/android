package com.ssafy.neegongnaegong.presentation.util

import com.ssafy.neegongnaegong.domain.usecase.auth.ReissueUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    private val reissueUseCase: ReissueUseCase
) {
    private val _isValid = MutableStateFlow(false)
    val isValid: StateFlow<Boolean> = _isValid

    suspend fun tryAuth(setLoading: (Boolean) -> Unit = {}) {
        reissueUseCase().onStart {
            setLoading(true)
        }.onCompletion {
            setLoading(false)
        }.catch {
            invalid()
        }.collect { isSuccess ->
            if (isSuccess) valid()
            else invalid()
        }
    }

    private fun valid() {
        _isValid.value = true
    }

    fun invalid() {
        _isValid.value = false
    }
}
