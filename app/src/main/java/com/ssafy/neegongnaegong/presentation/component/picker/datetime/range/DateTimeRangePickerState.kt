package com.ssafy.neegongnaegong.presentation.component.picker.datetime.range

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
fun rememberDateTimeRangePickerState(
    startDateTime: LocalDateTime = LocalDateTime.now().with(LocalTime.MIN),
    endDateTime: LocalDateTime = startDateTime.plusHours(1),
    isAllDay: Boolean = false
): DateTimeRangePickerState {
    require(startDateTime <= endDateTime) {
        "startDateTime must be before or equal to endDateTime"
    }

    return remember {
        DateTimeRangePickerState(startDateTime, endDateTime, isAllDay)
    }
}

@Stable
class DateTimeRangePickerState(
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    isAllDay: Boolean
) {
    private var _startDateTime by mutableStateOf(startDateTime)
    val startDateTime: LocalDateTime get() = _startDateTime

    private var _endDateTime by mutableStateOf(endDateTime)
    val endDateTime: LocalDateTime get() = _endDateTime

    private var _focus by mutableStateOf(Focus.None)
    val focus: Focus get() = _focus

    fun setStartDate(date: LocalDate) {
        setStartDateTime(startDateTime.with(date))
    }

    fun setStartTime(time: LocalTime) {
        setStartDateTime(startDateTime.with(time))
    }

    fun setStartDateTime(dateTime: LocalDateTime) {
        _startDateTime = dateTime
        if (dateTime.isAfter(endDateTime)) {
            _endDateTime = dateTime
        }
    }

    fun setEndDate(date: LocalDate) {
        setEndDateTime(endDateTime.with(date))
    }

    fun setEndTime(time: LocalTime) {
        setEndDateTime(endDateTime.with(time))
    }

    fun setEndDateTime(dateTime: LocalDateTime) {
        _endDateTime = dateTime
        if (dateTime.isBefore(startDateTime)) {
            _startDateTime = dateTime
        }
    }

    private var _isAllDay by mutableStateOf(isAllDay)
    val isAllDay: Boolean get() = _isAllDay

    fun setIsAllDay(isAllDay: Boolean) {
        _isAllDay = isAllDay
        if (isAllDay) {
            setStartTime(LocalTime.MIN)
            setEndTime(LocalTime.MAX)
        }
    }

    val isFocused: Boolean
        get() = focus != Focus.None
    val isStartDateFocused: Boolean
        get() = focus == Focus.StartDate
    val isStartTimeFocused: Boolean
        get() = focus == Focus.StartTime
    val isEndDateFocused: Boolean
        get() = focus == Focus.EndDate
    val isEndTimeFocused: Boolean
        get() = focus == Focus.EndTime

    fun clearFocus() {
        _focus = Focus.None
    }

    fun focusOnStartDate() {
        _focus = Focus.StartDate
    }

    fun focusOnStartTime() {
        _focus = Focus.StartTime
    }

    fun focusOnEndDate() {
        _focus = Focus.EndDate
    }

    fun focusOnEndTime() {
        _focus = Focus.EndTime
    }

    enum class Focus {
        None,
        StartDate,
        StartTime,
        EndDate,
        EndTime,
    }
}