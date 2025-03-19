package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.time.DayOfWeek

val dayOfWeekOrder = listOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY
)

val DayOfWeek.color
    @Composable
    get() = when (this) {
        DayOfWeek.SUNDAY -> Color.Red
        DayOfWeek.SATURDAY -> Color.Blue
        else -> MaterialTheme.colorScheme.onBackground
    }
