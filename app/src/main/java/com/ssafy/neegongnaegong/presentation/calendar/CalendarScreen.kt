package com.ssafy.neegongnaegong.presentation.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import com.ssafy.neegongnaegong.presentation.calendar.component.ScheduleInput
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.CalendarState
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.ScheduleCalendar
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.rememberCalendarState
import com.ssafy.neegongnaegong.presentation.calendar.component.dialog.CalendarScheduleDialog
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToScheduleCreate: (LocalDate) -> Unit,
    navigateToScheduleDetail: (Schedule) -> Unit,
    navigateToScheduleEdit: (Schedule) -> Unit,
) {
    BackHandler { popBackStack() }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalendarContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState,
        onMonthSelected = { viewModel.setEvent(CalendarContract.Event.OnMonthSelected(it)) },
        onDateSelected = { viewModel.setEvent(CalendarContract.Event.OnDateSelected(it)) },
        onDialogDismissRequest = { viewModel.setEvent(CalendarContract.Event.OnDialogDismissed) },
        onScheduleClicked = { viewModel.setEvent(CalendarContract.Event.OnScheduleClicked(it)) },
        createSchedule = { date, title ->
            viewModel.setEvent(CalendarContract.Event.OnCreateScheduleClicked(date, title))
        },
        navigateToScheduleDetail = navigateToScheduleDetail,
        navigateToScheduleCreate = navigateToScheduleCreate,
        navigateToEditSchedule = navigateToScheduleEdit
    )
}

@Composable
fun CalendarContent(
    modifier: Modifier = Modifier,
    effect: Flow<CalendarContract.Effect>,
    uiState: CalendarContract.State,
    onMonthSelected: (YearMonth) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onDialogDismissRequest: () -> Unit,
    onScheduleClicked: (Schedule) -> Unit,
    createSchedule: (LocalDate, String) -> Unit,
    navigateToScheduleDetail: (Schedule) -> Unit,
    navigateToScheduleCreate: (LocalDate) -> Unit,
    navigateToEditSchedule: (Schedule) -> Unit,
) {
    val calendarState = rememberCalendarState(uiState.selectedDate)

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                is CalendarContract.Effect.NavigateToScheduleDetailScreen -> navigateToScheduleDetail(
                    effect.schedule
                )

                is CalendarContract.Effect.NavigateToScheduleEditScreen -> navigateToEditSchedule(
                    effect.schedule
                )

                is CalendarContract.Effect.NavigateToCreateScheduleScreen -> navigateToScheduleCreate(
                    effect.date
                )
            }
        }
    }

    CalendarScreen(
        modifier = modifier,
        calendarState = calendarState,
        schedules = uiState.schedules,
        isOnCreate = uiState.isOnCreate,
        onMonthSelected = onMonthSelected,
        onDateSelected = onDateSelected,
        createSchedule = createSchedule,
    )

    if (uiState.isLoading) LoadingDialog()

    if (uiState.isCalendarDialogShow) CalendarScheduleDialog(
        state = calendarState,
        onMonthChanged = onMonthSelected,
        onDateSelected = onDateSelected,
        onDismissRequest = onDialogDismissRequest,
        schedules = uiState.schedules,
        isOnCreate = uiState.isOnCreate,
        onSubmit = createSchedule,
        onScheduleClick = onScheduleClicked,
    )
}

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    calendarState: CalendarState,
    isOnCreate: Boolean,
    schedules: Map<LocalDate, List<Schedule>>,
    onMonthSelected: (YearMonth) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    createSchedule: (LocalDate, String) -> Unit,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        ScheduleCalendar(
            modifier = Modifier.weight(1f),
            state = calendarState,
            onMonthChanged = onMonthSelected,
            onDateSelected = onDateSelected,
            schedules = schedules
        )
        ScheduleInput(
            selectedDate = calendarState.date,
            isLoading = isOnCreate,
            onSubmit = createSchedule,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewCalendarScreen() {
    val calendarState = rememberCalendarState()
    val isOnCreate = true
    val now = LocalDateTime.now()
    val schedules = mutableMapOf<LocalDate, List<Schedule>>().apply {
        put(
            now.toLocalDate(),
            listOf(
                Schedule(
                    type = ScheduleType.PERSONAL,
                    id = 1,
                    info = ScheduleInfo(
                        title = "Meeting",
                        content = "Meeting",
                        startAt = now,
                        endAt = now.plusHours(1),
                        isAllDay = false,
                    ),
                ),
                Schedule(
                    type = ScheduleType.PERSONAL,
                    id = 2,
                    info = ScheduleInfo(
                        title = "Lunch",
                        content = "Lunch",
                        startAt = now,
                        endAt = now.plusHours(1),
                        isAllDay = false,
                    )
                ),
            )
        )
    }

    NeeGongNaeGongTheme {
        CalendarScreen(
            calendarState = calendarState,
            isOnCreate = isOnCreate,
            schedules = schedules,
            onMonthSelected = { },
            onDateSelected = { },
            createSchedule = { _, _ -> }
        )

        CalendarScheduleDialog(
            state = calendarState,
            onDateSelected = {},
            onMonthChanged = {},
            onDismissRequest = {},
            schedules = schedules,
            onSubmit = { _, _ -> },
            isOnCreate = isOnCreate,
            onScheduleClick = {},
        )
    }
}
