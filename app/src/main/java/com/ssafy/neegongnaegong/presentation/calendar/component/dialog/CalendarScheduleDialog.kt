package com.ssafy.neegongnaegong.presentation.calendar.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import com.ssafy.neegongnaegong.presentation.calendar.component.input.ScheduleInput
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.CalendarState
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.ScheduleInfoItem
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.rememberCalendarState
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

@Composable
fun CalendarScheduleDialog(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onMonthChanged: (YearMonth) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit = {},
    schedules: Map<LocalDate, List<Schedule>> = emptyMap(),
    isOnCreate: Boolean = false,
    onSubmit: (LocalDate, String) -> Unit = { _, _ -> },
    onScheduleClick: (Schedule) -> Unit = {},
) {
    CalendarDialog(
        modifier = modifier,
        state = state,
        onMonthChanged = onMonthChanged,
        onDateSelected = onDateSelected,
        onDismissRequest = onDismissRequest,
    ) { date ->
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                schedules[date]?.let { schedules ->
                    schedules.fastForEachIndexed { index, schedule ->
                        ScheduleInfoItem(
                            modifier = Modifier
                                .padding(1.dp)
                                .fillMaxWidth(),
                            schedule = schedule,
                            showPrefix = index == 0
                                    || schedule.info.startAt.toLocalTime() != schedules[index - 1].info.startAt.toLocalTime()
                                    || !(schedule.info.isAllDay && schedules[index - 1].info.isAllDay),
                            onClick = onScheduleClick
                        )
                    }
                }
            }
            ScheduleInput(
                selectedDate = date,
                onSubmit = onSubmit,
                isLoading = isOnCreate,
            )
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun CalendarScheduleDialogPreview() {
    val now: LocalDateTime = LocalDateTime.now()
    val calendarState = rememberCalendarState(now.toLocalDate())
    val schedules = mutableMapOf<LocalDate, List<Schedule>>().apply {
        put(
            now.toLocalDate(),
            mutableListOf(
                Schedule(
                    id = 1L,
                    type = ScheduleType.PERSONAL,
                    info = ScheduleInfo(
                        title = "Test Schedule",
                        content = "This is a test schedule",
                        startAt = now,
                        endAt = now.plusHours(1),
                    )
                ),
            )
        )
    }

    NeeGongNaeGongTheme {
        CalendarScheduleDialog(
            state = calendarState,
            schedules = schedules,
            onMonthChanged = {},
            onDateSelected = {},
        )
    }
}
