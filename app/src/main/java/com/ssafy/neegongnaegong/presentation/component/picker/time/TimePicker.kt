package com.ssafy.neegongnaegong.presentation.component.picker.time

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ssafy.neegongnaegong.presentation.component.LaunchedEffectAfterFirst
import com.ssafy.neegongnaegong.presentation.component.picker.ScrollPicker
import com.ssafy.neegongnaegong.presentation.component.picker.rememberScrollPickerState
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalTime

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime = LocalTime.now(),
    onTimeChange: (LocalTime) -> Unit = {},
) {
    val amPmList = remember { listOf("AM", "PM") }
    val hourList = remember { (0 until 24).toList() }
    val minuteList = remember { ((0 until 60)).toList() }

    val amPmPickerState = rememberScrollPickerState(if (selectedTime.hour < 12) "AM" else "PM")
    val hourPickerState = rememberScrollPickerState(selectedTime.hour)
    val minutePickerState = rememberScrollPickerState(selectedTime.minute)

    LaunchedEffectAfterFirst(
        hourPickerState.selectedItem,
        minutePickerState.selectedItem
    ) {
        onTimeChange(
            LocalTime.of(
                hourPickerState.selectedItem,
                minutePickerState.selectedItem
            )
        )
    }

    LaunchedEffectAfterFirst(amPmPickerState.selectedItem) {
        hourPickerState.updateSelectedItem(
            hourPickerState.selectedItem % 12 + if (amPmPickerState.selectedItem == "AM") 0 else 12
        )
    }

    LaunchedEffectAfterFirst(hourPickerState.selectedItem) {
        amPmPickerState.updateSelectedItem(
            if (hourPickerState.selectedItem < 12) "AM" else "PM"
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ScrollPicker(
            modifier = Modifier.weight(1f),
            items = amPmList,
            state = amPmPickerState,
            visibleItemsCount = 3,
            isInfinite = false
        )
        ScrollPicker(
            modifier = Modifier.weight(1f),
            items = hourList,
            state = hourPickerState,
            text = { (if (it % 12 == 0) 12 else it % 12).toString() },
        )
        Text(
            ":",
            style = NeeGongNaeGongTheme.typography.bodyMedium,
            color = NeeGongNaeGongTheme.colorScheme.primaryText
        )
        ScrollPicker(
            modifier = Modifier.weight(1f),
            items = minuteList,
            state = minutePickerState,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun TimePickerPreview() {
    NeeGongNaeGongTheme {
        TimePicker(
            selectedTime = LocalTime.of(10, 30),
            onTimeChange = {}
        )
    }
}
