package com.ssafy.neegongnaegong.presentation.component.picker.datetime

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DateTimePickerBody(
    modifier: Modifier = Modifier,
    dateTime: LocalDateTime,
    isDateFocused: Boolean,
    isTimeFocused: Boolean,
    onDateClicked: () -> Unit,
    onTimeClicked: () -> Unit,
    isTimeVisible: Boolean,
    enable: Boolean,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .clickable(
                    onClick = onDateClicked,
                    enabled = enable
                )
                .background(
                    color = if (isDateFocused) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                    shape = RoundedCornerShape(100)
                )
                .padding(4.dp),
            text = DateTimeFormatter.ofPattern("M월 d일 (E)", Locale.KOREAN).format(dateTime),
            color = MaterialTheme.colorScheme.onBackground,
            style = NeeGongNaeGongTheme.typography.bodyMedium
        )
        AnimatedVisibility(isTimeVisible) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .clickable(onClick = onTimeClicked, enabled = enable)
                    .background(
                        color = if (isTimeFocused) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                        shape = RoundedCornerShape(100)
                    )
                    .padding(4.dp),
                text = DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN).format(dateTime),
                color = MaterialTheme.colorScheme.onBackground,
                style = NeeGongNaeGongTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview
@Composable
private fun DateTimePickerBodyPreview() {
    DateTimePickerBody(
        dateTime = LocalDateTime.now(),
        onDateClicked = {},
        onTimeClicked = {},
        isDateFocused = false,
        isTimeFocused = false,
        isTimeVisible = true,
        enable = true,
    )
}
