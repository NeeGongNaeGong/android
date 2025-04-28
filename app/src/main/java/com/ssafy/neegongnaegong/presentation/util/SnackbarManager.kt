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

    suspend fun showMessage(message: String, type: Type = Type.None) {
        _message.emit(Message(message, type))
    }

    suspend fun showSuccessMessage(message: String) {
        showMessage(message, Type.Success)
    }

    suspend fun showWarningMessage(message: String) {
        showMessage(message, Type.Warning)
    }

    suspend fun showErrorMessage(message: String) {
        showMessage(message, Type.Error)
    }
}