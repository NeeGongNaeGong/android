package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayOfWeek(
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        dayOfWeekOrder.forEach { dayOfWeek ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN),
                style = MaterialTheme.typography.labelMedium,
                color = dayOfWeek.color,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun DayOfWeekPreview() {
    DayOfWeek(
        modifier = Modifier.fillMaxWidth()
    )
}
