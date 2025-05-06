package com.ssafy.neegongnaegong.presentation.component.picker.datetime.range

import android.util.Log
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
        "startDateTime($startDateTime) must be before or equal to endDateTime($endDateTime)"
    }

    return remember { DateTimeRangePickerState(startDateTime, endDateTime, isAllDay) }
}

@Stable
class DateTimeRangePickerState(
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    isAllDay: Boolean
) {
    var startDateTime by mutableStateOf(startDateTime)
        private set
    var endDateTime by mutableStateOf(endDateTime)
        private set
    var focus by mutableStateOf(Focus.None)
        private set
    var isAllDay by mutableStateOf(isAllDay)
        private set

    fun updateStartDate(date: LocalDate) = updateStartDateTime(startDateTime.with(date))
    fun updateStartTime(time: LocalTime) = updateStartDateTime(startDateTime.with(time))
    fun updateEndDate(date: LocalDate) = updateEndDateTime(this.endDateTime.with(date))
    fun updateEndTime(time: LocalTime) = updateEndDateTime(this.endDateTime.with(time))

    fun updateStartDateTime(dateTime: LocalDateTime) {
        startDateTime = dateTime
        if (dateTime.isAfter(this.endDateTime)) {
            endDateTime = dateTime
        }
    }

    fun updateEndDateTime(dateTime: LocalDateTime) {
        endDateTime = dateTime
        if (dateTime.isBefore(startDateTime)) {
            startDateTime = dateTime
        }
    }


    fun updateIsAllDay(isAllDay: Boolean) {
        this.isAllDay = isAllDay
        if (isAllDay) {
            updateStartTime(LocalTime.MIN)
            updateEndTime(LocalTime.MAX)
        }
    }

    fun toggleIsAllDay() = updateIsAllDay(!isAllDay)

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

    private fun updateFocus(focus: Focus) {
        this.focus = if (this.focus == focus) {
            Log.d("DateTimeRangePickerState", "$focus")
            Focus.None
        } else {
            focus
        }
    }

    fun clearFocus() = updateFocus(Focus.None)
    fun focusOnStartDate() = updateFocus(Focus.StartDate)
    fun focusOnStartTime() = updateFocus(Focus.StartTime)
    fun focusOnEndDate() = updateFocus(Focus.EndDate)
    fun focusOnEndTime() = updateFocus(Focus.EndTime)

    enum class Focus {
        None,
        StartDate,
        StartTime,
        EndDate,
        EndTime,
    }
}
