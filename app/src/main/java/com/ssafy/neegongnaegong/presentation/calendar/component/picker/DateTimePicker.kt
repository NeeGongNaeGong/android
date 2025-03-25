package com.ssafy.neegongnaegong.presentation.calendar.component.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DateTimePicker(
    modifier: Modifier = Modifier,
    dateTime: LocalDateTime,
    isDateFocused: Boolean = false,
    onDateClicked: () -> Unit = {},
    isTimeFocused: Boolean = false,
    onTimeClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = if (isDateFocused) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                    shape = RoundedCornerShape(100)
                )
                .padding(2.dp)
                .clickable(onClick = onDateClicked),
            text = DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.KOREAN).format(dateTime),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier
                .background(
                    color = if (isTimeFocused) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                    shape = RoundedCornerShape(100)
                )
                .padding(2.dp)
                .clickable(onClick = onTimeClicked),
            text = DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN).format(dateTime),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
private fun PreviewDateTimePicker() {
    NeeGongNaeGongTheme(dynamicColor = false) {
        DateTimePicker(dateTime = LocalDateTime.now(), isDateFocused = true)
    }
}