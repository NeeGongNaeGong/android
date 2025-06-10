package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
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
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = text,
            style = NeeGongNaeGongTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            color = NeeGongNaeGongTheme.colorScheme.primaryText
        )
        DayOfWeek()
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun CalendarHeaderPreview() {
    CalendarHeader(selectedMonth = YearMonth.now())
}
