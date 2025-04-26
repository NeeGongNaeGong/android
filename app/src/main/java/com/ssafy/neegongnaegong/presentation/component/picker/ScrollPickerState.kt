package com.ssafy.neegongnaegong.presentation.component.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun <T> rememberScrollPickerState(initialValue: T) = remember { ScrollPickerState(initialValue) }

@Stable
class ScrollPickerState<T> internal constructor(initialValue: T) {
    var selectedItem by mutableStateOf<T>(initialValue)
        private set

    fun updateSelectedItem(newValue: T) {
        selectedItem = newValue
    }
}
