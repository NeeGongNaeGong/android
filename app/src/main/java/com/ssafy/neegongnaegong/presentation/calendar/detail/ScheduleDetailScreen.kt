package com.ssafy.neegongnaegong.presentation.calendar.detail

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.presentation.calendar.component.CalendarTopAppBar
import com.ssafy.neegongnaegong.presentation.calendar.component.dialog.DeleteTypeSelectDialog
import com.ssafy.neegongnaegong.presentation.calendar.component.form.ScheduleInputFormFocus
import com.ssafy.neegongnaegong.presentation.calendar.component.input.ScheduleEditText
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.component.picker.datetime.range.DateTimeRangePickerBody
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun ScheduleDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: ScheduleDetailViewModel = hiltViewModel(),
    scheduleId: Long,
    date: LocalDate,
    popBackStack: () -> Unit,
    navigateToEditScheduleScreen: (Schedule, ScheduleInputFormFocus) -> Unit,
) {
    BackHandler {
        popBackStack()
    }

    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent(ScheduleDetailContract.Event.OnLoad(scheduleId, date))
    }

    ScheduleDetailContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onFormClick = { viewModel.setEvent(ScheduleDetailContract.Event.OnFormClick(it)) },
        onEditClick = { viewModel.setEvent(ScheduleDetailContract.Event.OnEditClick) },
        onDeleteClick = { viewModel.setEvent(ScheduleDetailContract.Event.OnDeleteClick) },
        onDeleteTypeSelected = { viewModel.setEvent(ScheduleDetailContract.Event.OnDeleteTypeSelected(it)) },
        onDialogDismissed = { viewModel.setEvent(ScheduleDetailContract.Event.OnDialogDismissed) },
        navigateToEditScheduleScreen = navigateToEditScheduleScreen,
    )
}

@Composable
fun ScheduleDetailContent(
    modifier: Modifier = Modifier,
    effect: Flow<ScheduleDetailContract.Effect>,
    uiState: ScheduleDetailContract.State,
    onFormClick: (ScheduleInputFormFocus) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteTypeSelected: (DeleteType) -> Unit,
    onDialogDismissed: () -> Unit,
    navigateToEditScheduleScreen: (Schedule, ScheduleInputFormFocus) -> Unit,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                ScheduleDetailContract.Effect.NavigateBack -> backDispatcher?.onBackPressed()

                is ScheduleDetailContract.Effect.NavigateToEditScheduleScreen -> {
                    navigateToEditScheduleScreen(effect.schedule, effect.focus)
                }
            }
        }
    }

    ScheduleDetailScreen(
        modifier = modifier,
        schedule = uiState.schedule.info,
        repeatRule = uiState.schedule.info.repeatRule?.info,
        onEditClick = onEditClick,
        onDeleteClick = onDeleteClick,
        onFormClick = onFormClick,
    )

    if (uiState.isLoading || uiState.isOnDelete) LoadingDialog()

    if (uiState.isDeleteTypeSelectorShow && lifecycleState.isAtLeast(Lifecycle.State.STARTED)) {
        DeleteTypeSelectDialog(
            repeatType = uiState.schedule.info.repeatRule?.info?.repeatType,
            onDismissRequest = onDialogDismissed,
            onDeleteTypeSelected = onDeleteTypeSelected,
        )
    }
}

@Composable
fun ScheduleDetailScreen(
    modifier: Modifier = Modifier,
    schedule: ScheduleInfo,
    repeatRule: RepeatRuleInfo?,
    onFormClick: (ScheduleInputFormFocus) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .then(modifier),
    ) {
        CalendarTopAppBar()

        Column(
            modifier =
                modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
        ) {
            ScheduleEditText(
                modifier =
                    Modifier
                        .clickable { onFormClick(ScheduleInputFormFocus.Title) }
                        .fillMaxWidth(),
                text = schedule.title,
                onTextChange = {},
                placeHolder = "제목",
                enable = false,
            )
            DateTimeRangePickerBody(
                modifier = modifier,
                startDateTime = schedule.startAt,
                endDateTime = schedule.endAt,
                isAllDay = schedule.isAllDay,
                isStartDateFocused = false,
                isStartTimeFocused = false,
                isEndDateFocused = false,
                isEndTimeFocused = false,
                onStartDateClicked = { onFormClick(ScheduleInputFormFocus.StartDate) },
                onStartTimeClicked = { onFormClick(ScheduleInputFormFocus.StartTime) },
                onEndDateClicked = { onFormClick(ScheduleInputFormFocus.EndDate) },
                onEndTimeClicked = { onFormClick(ScheduleInputFormFocus.EndTime) },
                onIsAllDayChanged = { onFormClick(ScheduleInputFormFocus.IsAllDay) },
            )
            if (!schedule.content.isNullOrEmpty()) {
                ScheduleEditText(
                    modifier =
                        Modifier
                            .clickable { onFormClick(ScheduleInputFormFocus.Content) }
                            .fillMaxWidth(),
                    text = schedule.content,
                    onTextChange = {},
                    placeHolder = "메모",
                    prefix = Icons.Outlined.Description,
                    enable = false,
                )
            }
            if (!schedule.location.isNullOrEmpty()) {
                ScheduleEditText(
                    modifier =
                        Modifier
                            .clickable { onFormClick(ScheduleInputFormFocus.Location) }
                            .fillMaxWidth(),
                    text = schedule.location,
                    onTextChange = {},
                    placeHolder = "장소",
                    prefix = Icons.Outlined.LocationOn,
                    enable = false,
                )
            }
            ScheduleEditText(
                modifier =
                    Modifier
                        .clickable { onFormClick(ScheduleInputFormFocus.RepeatRule) }
                        .fillMaxWidth(),
                text = repeatRule?.toDisplayString() ?: "반복 안 함",
                placeHolder = "반복 안 함",
                prefix = Icons.Outlined.Repeat,
                enable = false,
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = onDeleteClick,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    "삭제",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.peach,
                )
            }
            TextButton(
                onClick = onEditClick,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    "수정",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewScheduleDetailScreen() {
    NeeGongNaeGongTheme {
        ScheduleDetailScreen(
            modifier = Modifier.fillMaxSize(),
            schedule =
                ScheduleInfo(
                    title = "New Schedule",
                    content = null,
                    startAt = LocalDateTime.now(),
                    endAt = LocalDateTime.now().plusHours(1),
                    location = null,
                ),
            repeatRule =
                RepeatRuleInfo(
                    repeatType = RepeatType.MONTHLY,
                    repeatInterval = 1,
                    repeatDay = 3,
                    endDate = null,
                ),
            onFormClick = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}
