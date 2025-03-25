package com.ssafy.neegongnaegong.presentation.calendar.component.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalTime

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime = LocalTime.now(),
    onTimeChange: (LocalTime) -> Unit = {},
) {
    val amPmList = listOf("AM", "PM")
    val hourList = (1..12).toList()
    val minuteList = ((0..55 step 5)).toList()

    val amPmPickerState = rememberPickerState(if (selectedTime.hour < 12) "AM" else "PM")
    val hourPickerState = rememberPickerState(if (selectedTime.hour % 12 == 0) 12 else selectedTime.hour % 12)
    val minutePickerState = rememberPickerState(selectedTime.minute)

    LaunchedEffect(amPmPickerState.selectedItem, hourPickerState.selectedItem, minutePickerState.selectedItem) {
        onTimeChange(
            LocalTime.of(
                if (amPmPickerState.selectedItem == "AM") {
                    if (hourPickerState.selectedItem == 12) 0 else hourPickerState.selectedItem
                } else {
                    if (hourPickerState.selectedItem == 12) 12 else hourPickerState.selectedItem + 12
                },
                minutePickerState.selectedItem
            )
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
        )
        ScrollPicker(
            modifier = Modifier.weight(1f),
            items = hourList,
            state = hourPickerState,
        )
        Text(
            ":",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        ScrollPicker(
            modifier = Modifier.weight(1f),
            items = minuteList,
            state = minutePickerState,
        )
    }
}

@Preview
@Composable
private fun TimePickerPreview() {
    NeeGongNaeGongTheme {
        Surface {
            TimePicker(
                selectedTime = LocalTime.of(10, 30),
                onTimeChange = {}
            )
        }
    }
}
