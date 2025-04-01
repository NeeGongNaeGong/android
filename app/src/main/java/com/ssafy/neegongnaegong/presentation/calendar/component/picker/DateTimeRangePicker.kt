package com.ssafy.neegongnaegong.presentation.calendar.component.picker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.calendar.component.ScheduleEditText
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime
import java.time.YearMonth

private enum class DateTimePickerState {
    UNFOCUSED,
    START_DATE_FOCUSED,
    START_TIME_FOCUSED,
    END_DATE_FOCUSED,
    END_TIME_FOCUSED,
}

@Composable
fun DateTimeRangePicker(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    isAllDay: Boolean,
    onStartDateTimeChange: (LocalDateTime) -> Unit = {},
    onEndDateTimeChange: (LocalDateTime) -> Unit = {},
    onIsAllDayToggle: (Boolean) -> Unit = {}
) {
    var focusState by remember { mutableStateOf(DateTimePickerState.UNFOCUSED) }

    Column(modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ScheduleEditText(
                modifier = Modifier.weight(1f),
                text = "하루 종일",
                enabled = false,
                prefix = Icons.Outlined.Schedule,
            )
            Switch(
                modifier = Modifier
                    .scale(0.75f)
                    .padding(end = 16.dp),
                checked = isAllDay,
                onCheckedChange = onIsAllDayToggle,
                colors = SwitchDefaults.colors().copy(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    checkedBorderColor = MaterialTheme.colorScheme.primary,
                    checkedIconColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onBackground,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    uncheckedBorderColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            DateTimePicker(
                modifier = Modifier.weight(1f),
                dateTime = startDateTime,
                isDateFocused = focusState == DateTimePickerState.START_DATE_FOCUSED,
                isTimeFocused = focusState == DateTimePickerState.START_TIME_FOCUSED,
                onDateClicked = {
                    focusState = if (focusState == DateTimePickerState.START_DATE_FOCUSED) {
                        DateTimePickerState.UNFOCUSED
                    } else {
                        DateTimePickerState.START_DATE_FOCUSED
                    }
                },
                onTimeClicked = {
                    focusState = if (focusState == DateTimePickerState.START_TIME_FOCUSED) {
                        DateTimePickerState.UNFOCUSED
                    } else {
                        DateTimePickerState.START_TIME_FOCUSED
                    }
                },
                isTimeVisible = !isAllDay
            )
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowForward,
                    "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            DateTimePicker(
                modifier = Modifier.weight(1f),
                dateTime = endDateTime,
                isDateFocused = focusState == DateTimePickerState.END_DATE_FOCUSED,
                isTimeFocused = focusState == DateTimePickerState.END_TIME_FOCUSED,
                onDateClicked = {
                    focusState = if (focusState == DateTimePickerState.END_DATE_FOCUSED) {
                        DateTimePickerState.UNFOCUSED
                    } else {
                        DateTimePickerState.END_DATE_FOCUSED
                    }
                },
                onTimeClicked = {
                    focusState = if (focusState == DateTimePickerState.END_TIME_FOCUSED) {
                        DateTimePickerState.UNFOCUSED
                    } else {
                        DateTimePickerState.END_TIME_FOCUSED
                    }
                },
                isTimeVisible = !isAllDay
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(focusState == DateTimePickerState.START_DATE_FOCUSED || focusState == DateTimePickerState.END_DATE_FOCUSED) {
            DateRangePicker(
                initialDate = if (focusState == DateTimePickerState.START_DATE_FOCUSED) startDateTime.toLocalDate() else endDateTime.toLocalDate(),
                initialMonth = YearMonth.from(startDateTime),
                startDate = startDateTime.toLocalDate(),
                endDate = endDateTime.toLocalDate(),
                onDateSelected = {
                    if (focusState == DateTimePickerState.START_DATE_FOCUSED) onStartDateTimeChange(
                        it.atTime(startDateTime.toLocalTime())
                    )
                    if (focusState == DateTimePickerState.END_DATE_FOCUSED) onEndDateTimeChange(
                        it.atTime(endDateTime.toLocalTime())
                    )
                }
            )
        }
        AnimatedVisibility(visible = focusState == DateTimePickerState.START_TIME_FOCUSED || focusState == DateTimePickerState.END_TIME_FOCUSED) {
            Crossfade(targetState = focusState) { state ->
                when (state) {
                    DateTimePickerState.START_TIME_FOCUSED -> TimePicker(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        selectedTime = startDateTime.toLocalTime(),
                        onTimeChange = { onStartDateTimeChange(it.atDate(startDateTime.toLocalDate())) }
                    )

                    DateTimePickerState.END_TIME_FOCUSED -> TimePicker(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        selectedTime = endDateTime.toLocalTime(),
                        onTimeChange = { onEndDateTimeChange(it.atDate(startDateTime.toLocalDate())) }
                    )

                    else -> {}
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDateTimeRangePicker() {
    NeeGongNaeGongTheme(dynamicColor = false) {
        Surface {
            DateTimeRangePicker(
                startDateTime = LocalDateTime.of(2024, 1, 1, 0, 0),
                endDateTime = LocalDateTime.of(2024, 1, 2, 23, 59),
                isAllDay = true,
            )
        }
    }
}