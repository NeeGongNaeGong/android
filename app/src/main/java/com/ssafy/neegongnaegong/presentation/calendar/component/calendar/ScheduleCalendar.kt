package com.ssafy.neegongnaegong.presentation.calendar.component.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

@Composable
fun ScheduleCalendar(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onMonthChanged: (YearMonth) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    schedules: Map<LocalDate, List<Schedule>> = emptyMap()
) {
    Calendar(
        modifier = modifier,
        state = state,
        onMonthChanged = onMonthChanged,
        onDateSelected = onDateSelected,
    ) { date ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 2.dp)
        ) {
            schedules[date]?.take(3)?.forEach { schedule ->
                ScheduleSummary(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp),
                    title = schedule.info.title
                )
            }
        }
    }
}

@Preview
@Composable
fun ScheduleCalendarPreview() {
    val state = rememberCalendarState()
    val schedules = mutableMapOf<LocalDate, List<Schedule>>()
    schedules[LocalDate.now()] = listOf(
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 1,
            info = ScheduleInfo(
                title = "Meeting",
                content = "Meeting",
                startAt = LocalDateTime.now(),
                endAt = LocalDateTime.now().plusHours(1),
                isAllDay = false,
            )
        ),
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 2,
            info = ScheduleInfo(
                title = "Lunch",
                content = "Lunch",
                startAt = LocalDateTime.now(),
                endAt = LocalDateTime.now().plusHours(1),
                isAllDay = false,
            )
        ),
    )

    NeeGongNaeGongTheme {
        ScheduleCalendar(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onDateSelected = {},
            onMonthChanged = {},
            schedules = schedules,
        )
    }
}
