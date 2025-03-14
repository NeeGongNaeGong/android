package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarBody(
    modifier: Modifier = Modifier,
    selectedMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    dateContent: @Composable (LocalDate) -> Unit = {},
) {
    val lastDay by remember { mutableIntStateOf(selectedMonth.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableIntStateOf(selectedMonth.atDay(1).dayOfWeek.value % 7) }

    Column(modifier = modifier) {
        DayOfWeek()
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            val totalRows = (firstDayOfWeek + lastDay + 6) / 7
            for (row in 0 until totalRows) {
                Row(modifier = Modifier.weight(1f)) {
                    for (column in 0..6) {
                        val calendarIndex = row * 7 + column
                        val dayOffset = calendarIndex - firstDayOfWeek + 1
                        val date = if (dayOffset < 1) {
                            val previousMonth = selectedMonth.minusMonths(1)
                            val lastDayOfPreviousMonth = previousMonth.lengthOfMonth()
                            previousMonth.atDay(lastDayOfPreviousMonth + dayOffset)
                        } else if (dayOffset > lastDay) {
                            val nextMonth = selectedMonth.plusMonths(1)
                            nextMonth.atDay(dayOffset - lastDay)
                        } else {
                            selectedMonth.atDay(dayOffset)
                        }

                        CalendarCell(
                            modifier = Modifier
                                .weight(1f)
                                .alpha(if (dayOffset in 1..lastDay) 1f else 0.3f),
                            date = date,
                            isSelected = remember(selectedDate) {
                                selectedDate.compareTo(date) == 0
                            },
                            onSelect = onDateSelected,
                        ) {
                            dateContent(date)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CalendarBodyPreview() {
    val currentMonth = remember { YearMonth.now() }
    val currentDate = remember { LocalDate.now() }

    NeeGongNaeGongTheme {
        CalendarBody(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            selectedMonth = currentMonth,
            selectedDate = currentDate,
            onDateSelected = { }
        )
    }
}