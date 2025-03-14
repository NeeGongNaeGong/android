package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.data.model.calendar.Schedule
import com.ssafy.neegongnaegong.data.model.calendar.ScheduleType
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

@Composable
fun ScheduleCalendar(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    initialMonth: YearMonth = YearMonth.now(),
    minMonth: YearMonth = YearMonth.of(1900, 1),
    maxMonth: YearMonth = YearMonth.of(2100, 12),
    onMonthChanged: (YearMonth) -> Unit = {},
    onDateSelected: (LocalDate) -> Unit = {},
    schedules: Map<LocalDate, List<Schedule>> = emptyMap()
) {
    Calendar(
        modifier = modifier,
        initialDate = initialDate,
        initialMonth = initialMonth,
        minMonth = minMonth,
        maxMonth = maxMonth,
        onMonthChanged = onMonthChanged,
        onDateSelected = onDateSelected,
    ) { date ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
        ) {
            schedules[date]?.take(3)?.forEach { schedule ->
                ScheduleSummary(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp),
                    title = schedule.title
                )
            }
        }
    }
}

@Preview
@Composable
fun ScheduleCalendarPreview() {
    val schedules = mutableMapOf<LocalDate, List<Schedule>>()
    schedules[LocalDate.now()] = listOf(
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 1,
            title = "Meeting",
            content = "Meeting",
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now().plusHours(1)
        ),
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 2,
            title = "Lunch",
            content = "Lunch",
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now().plusHours(1)
        ),
    )

    NeeGongNaeGongTheme {
        ScheduleCalendar(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            schedules = schedules,
        )
    }
}