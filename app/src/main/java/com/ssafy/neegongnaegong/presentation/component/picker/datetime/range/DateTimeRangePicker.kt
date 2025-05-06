package com.ssafy.neegongnaegong.presentation.component.picker.datetime.range

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.component.picker.date.range.DateRangePicker
import com.ssafy.neegongnaegong.presentation.component.picker.date.range.rememberDateRangePickerState
import com.ssafy.neegongnaegong.presentation.component.picker.time.TimePicker
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime
import java.time.YearMonth

@Composable
fun DateTimeRangePicker(
    modifier: Modifier = Modifier,
    state: DateTimeRangePickerState,
    onStartDateTimeChange: (LocalDateTime) -> Unit = {},
    onEndDateTimeChange: (LocalDateTime) -> Unit = {},
    enable: Boolean = true,
) {
    val dateRangePickerState = rememberDateRangePickerState(
        startDate = state.startDateTime.toLocalDate(),
        endDate = state.endDateTime.toLocalDate(),
        autoFocus = false,
    )

    LaunchedEffect(state.focus) {
        when (state.focus) {
            DateTimeRangePickerState.Focus.StartDate -> dateRangePickerState.focusOnStart()
            DateTimeRangePickerState.Focus.EndDate -> dateRangePickerState.focusOnEnd()
            else -> {}
        }
    }

    LaunchedEffect(state.startDateTime) {
        dateRangePickerState.updateStartDate(state.startDateTime.toLocalDate())
        onStartDateTimeChange(state.startDateTime)
    }

    LaunchedEffect(state.endDateTime) {
        dateRangePickerState.updateEndDate(state.endDateTime.toLocalDate())
        onEndDateTimeChange(state.endDateTime)
    }

    LaunchedEffect(dateRangePickerState.startDate) {
        state.updateStartDate(dateRangePickerState.startDate)
    }

    LaunchedEffect(dateRangePickerState.endDate) {
        state.updateEndDate(dateRangePickerState.endDate)
    }

    Column(modifier = modifier) {
        DateTimeRangePickerBody(
            modifier = modifier,
            startDateTime = state.startDateTime,
            endDateTime = state.endDateTime,
            isAllDay = state.isAllDay,
            isStartDateFocused = state.isStartDateFocused,
            isStartTimeFocused = state.isStartTimeFocused,
            isEndDateFocused = state.isEndDateFocused,
            isEndTimeFocused = state.isEndTimeFocused,
            onStartDateClicked = state::focusOnStartDate,
            onStartTimeClicked = state::focusOnStartTime,
            onEndDateClicked = state::focusOnEndDate,
            onEndTimeClicked = state::focusOnEndTime,
            onIsAllDayChanged = state::updateIsAllDay,
            enable = enable
        )
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(state.isFocused) {
            Crossfade(targetState = state.focus) { focus ->
                when (focus) {
                    DateTimeRangePickerState.Focus.None -> {}
                    DateTimeRangePickerState.Focus.StartDate, DateTimeRangePickerState.Focus.EndDate -> {
                        DateRangePicker(
                            initialMonth = YearMonth.from(state.startDateTime),
                            state = dateRangePickerState,
                            onStartDateSelected = state::updateStartDate,
                            onEndDateSelected = state::updateEndDate,
                        )
                    }

                    DateTimeRangePickerState.Focus.StartTime -> {
                        TimePicker(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            selectedTime = state.startDateTime.toLocalTime(),
                            onTimeChange = state::updateStartTime
                        )
                    }

                    DateTimeRangePickerState.Focus.EndTime -> {
                        TimePicker(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            selectedTime = state.endDateTime.toLocalTime(),
                            onTimeChange = state::updateEndTime
                        )
                    }
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateTimeRangePickerPreview_Focus_None() {
    val state = rememberDateTimeRangePickerState(
        startDateTime = LocalDateTime.now(),
        endDateTime = LocalDateTime.now().plusDays(1)
    )

    NeeGongNaeGongTheme {
        DateTimeRangePicker(state = state, onStartDateTimeChange = {}, onEndDateTimeChange = {})
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateTimeRangePickerPreview_Focus_Date() {
    val state = rememberDateTimeRangePickerState(
        startDateTime = LocalDateTime.now(),
        endDateTime = LocalDateTime.now().plusDays(1)
    ).apply {
        focusOnStartDate()
    }

    NeeGongNaeGongTheme {
        DateTimeRangePicker(state = state, onStartDateTimeChange = {}, onEndDateTimeChange = {})
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateTimeRangePickerPreview_Focus_Time() {
    val state = rememberDateTimeRangePickerState(
        startDateTime = LocalDateTime.now(),
        endDateTime = LocalDateTime.now().plusDays(1)
    ).apply {
        focusOnStartTime()
    }

    NeeGongNaeGongTheme {
        DateTimeRangePicker(state = state, onStartDateTimeChange = {}, onEndDateTimeChange = {})
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateTimeRangePickerPreview_Is_All_Day() {
    val state = rememberDateTimeRangePickerState(
        startDateTime = LocalDateTime.now(),
        endDateTime = LocalDateTime.now().plusDays(1)
    ).apply {
        updateIsAllDay(true)
    }

    NeeGongNaeGongTheme {
        DateTimeRangePicker(state = state, onStartDateTimeChange = {}, onEndDateTimeChange = {})
    }
}