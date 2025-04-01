package com.ssafy.neegongnaegong.presentation.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import com.ssafy.neegongnaegong.presentation.calendar.component.ScheduleInput
import com.ssafy.neegongnaegong.presentation.calendar.component.calendar.ScheduleCalendar
import com.ssafy.neegongnaegong.presentation.calendar.component.dialog.CalendarScheduleDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

@Composable
fun CalendarRoute(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateToScheduleDetail: (Schedule) -> Unit,
    navigateToScheduleCreate: (LocalDate) -> Unit,
) {
    BackHandler {
        popBackStack()
    }

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
            viewModel.setEvent(
                CalendarContract.Event.OnCreateScheduleClicked(
                    date,
                    title
                )
            )
        },
        navigateToScheduleDetail = navigateToScheduleDetail,
        navigateToScheduleCreate = navigateToScheduleCreate,
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
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                is CalendarContract.Effect.NavigateToScheduleDetailScreen -> navigateToScheduleDetail(
                    effect.schedule
                )

                is CalendarContract.Effect.NavigateToCreateScheduleScreen -> navigateToScheduleCreate(
                    effect.date
                )

                is CalendarContract.Effect.ShowErrorSnackBar -> scope.launch {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = "확인",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    CalendarScreen(
        modifier = modifier,
        selectedDate = uiState.selectedDate,
        schedules = uiState.schedules,
        onMonthSelected = onMonthSelected,
        onDateSelected = onDateSelected,
        createSchedule = createSchedule,
    )

    if (uiState.isCalendarDialogShow) CalendarScheduleDialog(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                RoundedCornerShape(20.dp)
            )
            .height(screenHeight * 0.7f)
            .padding(20.dp),
        onDismissRequest = onDialogDismissRequest,
        date = uiState.selectedDate,
        schedules = uiState.schedules[uiState.selectedDate] ?: emptyList(),
        onSubmit = createSchedule,
        onScheduleClick = onScheduleClicked,
    )
}

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
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
            onMonthChanged = onMonthSelected,
            onDateSelected = onDateSelected,
            schedules = schedules
        )
        ScheduleInput(
            selectedDate = selectedDate,
            onSubmit = createSchedule
        )
    }
}

@Preview
@Composable
private fun PreviewCalendarScreen() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val schedules = listOf(
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 1,
            info = ScheduleInfo(
                title = "Meeting",
                content = "Meeting",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
                isAllDay = false,
            ),
        ),
        Schedule(
            type = ScheduleType.PERSONAL,
            id = 2,
            info = ScheduleInfo(
                title = "Lunch",
                content = "Lunch",
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
                isAllDay = false,
            )
        ),
    )

    NeeGongNaeGongTheme(dynamicColor = false) {
        Surface {
            CalendarScreen(
                selectedDate = LocalDate.now(),
                schedules = mapOf(LocalDate.now() to schedules),
                onMonthSelected = { },
                onDateSelected = { },
                createSchedule = { _, _ -> }
            )

            CalendarScheduleDialog(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        RoundedCornerShape(20.dp)
                    )
                    .height(screenHeight * 0.7f)
                    .padding(20.dp),
                onDismissRequest = {},
                date = LocalDate.now(),
                schedules = schedules,
                onSubmit = { _, _ -> },
                onScheduleClick = {},
            )
        }
    }
}
