package com.ssafy.neegongnaegong.presentation.util

import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthDestinationManager {
    private val _destination = MutableStateFlow<Any?>(null)
    val destination: StateFlow<Any?> = _destination.asStateFlow()

    fun invalid() {
        _destination.value = AppNavigation.Login
    }

    fun valid() {
        _destination.value = AppNavigation.Tab.Studies
    }
}
