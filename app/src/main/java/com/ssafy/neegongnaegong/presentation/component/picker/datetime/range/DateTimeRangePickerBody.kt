package com.ssafy.neegongnaegong.presentation.component.picker.datetime.range

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.calendar.component.ScheduleEditText
import com.ssafy.neegongnaegong.presentation.calendar.component.switchColors
import com.ssafy.neegongnaegong.presentation.component.picker.datetime.DateTimePickerBody
import java.time.LocalDateTime

@Composable
fun DateTimeRangePickerBody(
    modifier: Modifier = Modifier,
    startDateTime: LocalDateTime,
    endDateTime: LocalDateTime,
    isAllDay: Boolean,
    isStartDateFocused: Boolean,
    isStartTimeFocused: Boolean,
    isEndDateFocused: Boolean,
    isEndTimeFocused: Boolean,
    onStartDateClicked: () -> Unit,
    onStartTimeClicked: () -> Unit,
    onEndDateClicked: () -> Unit,
    onEndTimeClicked: () -> Unit,
    onIsAllDayChanged: (Boolean) -> Unit,
    enable: Boolean,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
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
            onCheckedChange = onIsAllDayChanged,
            enabled = enable,
            colors = MaterialTheme.switchColors(),
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        DateTimePickerBody(
            modifier = Modifier.weight(1f),
            dateTime = startDateTime,
            isDateFocused = isStartDateFocused,
            isTimeFocused = isStartTimeFocused,
            onDateClicked = onStartDateClicked,
            onTimeClicked = onStartTimeClicked,
            isTimeVisible = !isAllDay,
            enable = enable,
        )
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Icon(
                Icons.AutoMirrored.Outlined.ArrowForward,
                "",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        DateTimePickerBody(
            modifier = Modifier.weight(1f),
            dateTime = endDateTime,
            isDateFocused = isEndDateFocused,
            isTimeFocused = isEndTimeFocused,
            onDateClicked = onEndDateClicked,
            onTimeClicked = onEndTimeClicked,
            isTimeVisible = !isAllDay,
            enable = enable,
        )
    }
}

@Preview
@Composable
private fun DateTimeRangePickerPreview() {
    DateTimeRangePickerBody(
        modifier = Modifier.padding(16.dp),
        startDateTime = LocalDateTime.now(),
        endDateTime = LocalDateTime.now().plusDays(1),
        isAllDay = false,
        isStartDateFocused = false,
        isEndDateFocused = false,
        isStartTimeFocused = false,
        isEndTimeFocused = false,
        onStartDateClicked = {},
        onEndDateClicked = {},
        onStartTimeClicked = {},
        onEndTimeClicked = {},
        onIsAllDayChanged = {},
        enable = true,
    )
}
