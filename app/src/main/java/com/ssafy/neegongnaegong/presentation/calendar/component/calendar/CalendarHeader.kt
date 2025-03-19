package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.time.YearMonth

@Composable
fun CalendarHeader(modifier: Modifier = Modifier, selectedMonth: YearMonth) {
    CalendarHeader(
        modifier = modifier,
        text = "${selectedMonth.year}년 ${selectedMonth.monthValue}월",
    )
}

@Composable
fun CalendarHeader(modifier: Modifier = Modifier, text: String) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = text,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
