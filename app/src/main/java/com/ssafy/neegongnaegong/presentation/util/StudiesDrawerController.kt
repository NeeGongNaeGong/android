package com.ssafy.neegongnaegong.presentation.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object StudiesDrawerController {
    private val _isOpen = MutableStateFlow(false)
    val isOpen: StateFlow<Boolean> = _isOpen.asStateFlow()

    fun open() {
        _isOpen.value = true
    }

    fun close() {
        _isOpen.value = false
    }
}
