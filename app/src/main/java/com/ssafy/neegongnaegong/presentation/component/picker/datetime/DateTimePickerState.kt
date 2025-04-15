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
    enum class Focus {
        None,
        Date,
        Time
    }

    private var _dateTime by mutableStateOf(initialDateTime)
    val dateTime: LocalDateTime get() = _dateTime

    private var _focus by mutableStateOf(Focus.None)
    val focus: Focus get() = _focus

    val isFocused: Boolean
        get() = focus != Focus.None
    val isDateFocused: Boolean
        get() = focus == Focus.Date
    val isTimeFocused: Boolean
        get() = focus == Focus.Time

    fun setDate(date: LocalDate) {
        setDateTime(dateTime.with(date))
    }

    fun setTime(time: LocalTime) {
        setDateTime(dateTime.with(time))
    }

    fun setDateTime(dateTime: LocalDateTime) {
        _dateTime = dateTime
    }

    fun focusOnDate() {
        setFocus(Focus.Date)
    }

    fun focusOnTime() {
        setFocus(Focus.Time)
    }

    fun clearFocus() {
        setFocus(Focus.None)
    }

    fun setFocus(focus: Focus) {
        if (this.focus == focus) _focus = Focus.None
        else _focus = focus
    }
}