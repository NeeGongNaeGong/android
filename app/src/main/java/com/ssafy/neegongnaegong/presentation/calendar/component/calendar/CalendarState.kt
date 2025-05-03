package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun rememberCalendarState(
    initialDate: LocalDate = LocalDate.now(),
    minMonth: YearMonth = YearMonth.of(1900, 1),
    maxMonth: YearMonth = YearMonth.of(2100, 12),
): CalendarState {
    return remember { CalendarState(initialDate, minMonth, maxMonth) }
}

@Stable
class CalendarState(
    initialDate: LocalDate,
    val minMonth: YearMonth,
    val maxMonth: YearMonth,
) {
    var date by mutableStateOf(initialDate)
        private set
    var month: YearMonth by mutableStateOf(YearMonth.from(initialDate))
        private set

    fun updateDate(newDate: LocalDate) {
        date = newDate
        YearMonth.from(newDate).let {
            if (month != it) month = it
        }
    }

    fun updateMonth(newMonth: YearMonth) {
        if (month != newMonth) {
            month = newMonth
            date = newMonth.atDay(1)
        }
    }
}