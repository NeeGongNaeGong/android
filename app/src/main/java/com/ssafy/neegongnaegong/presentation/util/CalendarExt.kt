package com.ssafy.neegongnaegong.presentation.util

import androidx.compose.runtime.Composable
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
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
        DayOfWeek.SUNDAY -> NeeGongNaeGongTheme.colorScheme.peach
        DayOfWeek.SATURDAY -> NeeGongNaeGongTheme.colorScheme.blue
        else -> NeeGongNaeGongTheme.colorScheme.primaryText
    }
