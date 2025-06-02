package com.ssafy.neegongnaegong.presentation.component.picker.datetime

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.component.picker.date.DatePicker
import com.ssafy.neegongnaegong.presentation.component.picker.date.rememberDatePickerState
import com.ssafy.neegongnaegong.presentation.component.picker.time.TimePicker
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    state: DateTimePickerState,
    onDateTimeChanged: (LocalDateTime) -> Unit,
    isTimeVisible: Boolean = true,
    enable: Boolean = true,
) {
    val datePickerState = rememberDatePickerState(state.dateTime.toLocalDate())

    LaunchedEffect(state.dateTime) {
        onDateTimeChanged(state.dateTime)
    }

    Row {
        DateTimePickerBody(
            modifier = modifier,
            dateTime = state.dateTime,
            isDateFocused = state.isDateFocused,
            isTimeFocused = state.isTimeFocused,
            onDateClicked = state::focusOnDate,
            onTimeClicked = state::focusOnTime,
            isTimeVisible = isTimeVisible,
            enable = enable
        )

        AnimatedVisibility(state.isFocused) {
            Spacer(modifier = Modifier.width(16.dp))
            Crossfade(targetState = state.focus) { focus ->
                when (focus) {
                    DateTimePickerState.Focus.None -> {}
                    DateTimePickerState.Focus.Date -> {
                        DatePicker(
                            state = datePickerState,
                            onDateSelected = state::updateDate,
                        )
                    }

                    DateTimePickerState.Focus.Time -> {
                        TimePicker(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            selectedTime = state.dateTime.toLocalTime(),
                            onTimeChange = state::updateTime
                        )
                    }
                }
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateTimePickerPreview_Focus_None() {
    val state = rememberDateTimePickerState()

    NeeGongNaeGongTheme {
        DateTimePicker(state = state, onDateTimeChanged = {})
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateTimePickerPreview_Focus_Date() {
    val state = rememberDateTimePickerState().apply {
        focusOnDate()
    }

    NeeGongNaeGongTheme {
        DateTimePicker(state = state, onDateTimeChanged = {})
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun DateTimePickerPreview_Focus_Time() {
    val state = rememberDateTimePickerState().apply {
        focusOnTime()
    }

    NeeGongNaeGongTheme {
        DateTimePicker(state = state, onDateTimeChanged = {})
    }
}
