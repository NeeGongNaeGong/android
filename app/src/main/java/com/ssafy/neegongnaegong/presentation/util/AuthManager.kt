package com.ssafy.neegongnaegong.presentation.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthManager {
    private val _auth = MutableStateFlow(false)
    val auth: StateFlow<Boolean> = _auth.asStateFlow()

    fun invalid() {
        _auth.value = false
    }

    fun valid() {
        _auth.value = true
    }
}
