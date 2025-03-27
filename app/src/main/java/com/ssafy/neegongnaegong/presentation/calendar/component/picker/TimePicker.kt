package com.ssafy.neegongnaegong.presentation.calendar.component.picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalTime

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime = LocalTime.now(),
    onTimeChange: (LocalTime) -> Unit = {},
) {
    val amPmList = listOf("AM", "PM")
    val hourList = (0 until 24).toList()
    val minuteList = ((0 until 60 step 5)).toList()

    val amPmPickerState = rememberPickerState(if (selectedTime.hour < 12) "AM" else "PM")
    val hourPickerState = rememberPickerState(selectedTime.hour)
    val minutePickerState = rememberPickerState(selectedTime.minute)

    LaunchedEffect(
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

    LaunchedEffect(amPmPickerState.selectedItem) {
        hourPickerState.selectedItem = hourPickerState.selectedItem % 12 +
                if (amPmPickerState.selectedItem == "AM") 0 else 12
    }

    LaunchedEffect(hourPickerState.selectedItem) {
        amPmPickerState.selectedItem = if (hourPickerState.selectedItem < 12) "AM" else "PM"
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
        ) { modifier, item ->
            Text(
                text = item,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier.padding(vertical = 4.dp)
            )
        }
        ScrollPicker(
            modifier = Modifier.weight(1f),
            items = hourList,
            state = hourPickerState,
        ) { modifier, item ->
            Text(
                text = (if (item % 12 == 0) 12 else item % 12).toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier.padding(vertical = 4.dp)
            )
        }
        Text(
            ":",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        ScrollPicker(
            modifier = Modifier.weight(1f),
            items = minuteList,
            state = minutePickerState,
        ) { modifier, item ->
            Text(
                text = item.toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = modifier.padding(vertical = 4.dp)
            )
        }
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
