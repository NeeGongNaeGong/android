package com.ssafy.neegongnaegong.presentation.calendar.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.presentation.calendar.component.ScheduleInput
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.CalendarState
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.rememberCalendarState
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate

@Composable
fun CalendarScheduleDialog(
    modifier: Modifier = Modifier,
    state: CalendarState,
    onDismissRequest: () -> Unit = {},
    schedules: List<Schedule> = emptyList(),
    isOnCreate: Boolean = false,
    onSubmit: (LocalDate, String) -> Unit = { _, _ -> },
    onScheduleClick: (Schedule) -> Unit = {},
) {
    CalendarDialog(
        modifier = modifier,
        state =  state,
        onDismissRequest = onDismissRequest,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                schedules.fastForEachIndexed { index, schedule ->
                    ScheduleInfo(
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
            ScheduleInput(
                selectedDate = state.date,
                onSubmit = onSubmit,
                isLoading = isOnCreate,
            )
        }
    }
}

@Preview
@Composable
fun CalendarScheduleDialogPreview() {
    val calendarState = rememberCalendarState()
    NeeGongNaeGongTheme(dynamicColor = false) {
        Surface {
            CalendarScheduleDialog(state = calendarState)
        }
    }
}
