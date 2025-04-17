package com.ssafy.neegongnaegong.presentation.component.picker.datetime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun rememberDateTimePickerState(
    initialDateTime: LocalDateTime = LocalDateTime.now(),
): DateTimePickerState {
    return remember { DateTimePickerState(initialDateTime) }
}

@Stable
class DateTimePickerState internal constructor(
    initialDateTime: LocalDateTime,
) {
    var dateTime by mutableStateOf(initialDateTime)
        private set
    var focus by mutableStateOf(Focus.None)
        private set

    val isFocused: Boolean
        get() = this.focus != Focus.None
    val isDateFocused: Boolean
        get() = this.focus == Focus.Date
    val isTimeFocused: Boolean
        get() = this.focus == Focus.Time

    fun updateDate(date: LocalDate) = updateDateTime(dateTime.with(date))
    fun updateTime(time: LocalTime) = updateDateTime(dateTime.with(time))

    fun updateDateTime(dateTime: LocalDateTime) {
        this.dateTime = dateTime
    }

    fun clearFocus() = updateFocus(Focus.None)
    fun focusOnDate() = updateFocus(Focus.Date)
    fun focusOnTime() = updateFocus(Focus.Time)

    private fun updateFocus(focus: Focus) {
        this.focus = if (this.focus == focus) {
            Focus.None
        } else {
            focus
        }
    }

    enum class Focus {
        None,
        Date,
        Time
    }
}