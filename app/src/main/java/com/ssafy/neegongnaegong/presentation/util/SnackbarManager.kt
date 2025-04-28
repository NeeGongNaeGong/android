package com.ssafy.neegongnaegong.presentation.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarManager @Inject constructor() {
    enum class Type {
        Success,
        Warning,
        Error,
        None
    }

    data class Message(val message: String, val type: Type)

    private val _message = MutableSharedFlow<Message>()
    val message: SharedFlow<Message> = _message

    suspend fun showToast(message: String, type: Type = Type.None) {
        _message.emit(Message(message, type))
    }

    suspend fun showSuccessToast(message: String) {
        showToast(message, Type.Success)
    }

    suspend fun showWarningToast(message: String) {
        showToast(message, Type.Warning)
    }

    suspend fun showErrorToast(message: String) {
        showToast(message, Type.Error)
    }
}