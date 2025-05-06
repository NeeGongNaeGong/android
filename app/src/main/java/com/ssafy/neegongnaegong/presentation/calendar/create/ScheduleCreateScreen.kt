package com.ssafy.neegongnaegong.presentation.calendar.create

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import com.ssafy.neegongnaegong.presentation.calendar.component.CalendarTopAppBar
import com.ssafy.neegongnaegong.presentation.calendar.component.form.ScheduleInputForm
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun ScheduleCreateRoute(
    modifier: Modifier = Modifier,
    viewModel: ScheduleCreateViewModel = hiltViewModel(),
    date: LocalDate,
    popBackStack: () -> Unit,
) {
    BackHandler {
        popBackStack()
    }

    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent(ScheduleCreateContract.Event.OnLoad(date))
    }

    ScheduleCreateContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onTitleChanged = { viewModel.setEvent(ScheduleCreateContract.Event.OnTitleChanged(it)) },
        onContentChanged = { viewModel.setEvent(ScheduleCreateContract.Event.OnContentChanged(it)) },
        onStartDateChanged = { viewModel.setEvent(ScheduleCreateContract.Event.OnStartDateChanged(it)) },
        onEndDateChanged = { viewModel.setEvent(ScheduleCreateContract.Event.OnEndDateChanged(it)) },
        onLocationChanged = { viewModel.setEvent(ScheduleCreateContract.Event.OnLocationChanged(it)) },
        onRepeatRuleChanged = {
            viewModel.setEvent(
                ScheduleCreateContract.Event.OnRepeatRuleChanged(
                    it
                )
            )
        },
        onSaveScheduleClicked = { viewModel.setEvent(ScheduleCreateContract.Event.OnCreateScheduleClicked) },
        onCancelClick = { viewModel.setEvent(ScheduleCreateContract.Event.OnCancelClick) }
    )
}

@Composable
fun ScheduleCreateContent(
    modifier: Modifier = Modifier,
    effect: Flow<ScheduleCreateContract.Effect>,
    uiState: ScheduleCreateContract.State,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onStartDateChanged: (LocalDateTime) -> Unit,
    onEndDateChanged: (LocalDateTime) -> Unit,
    onLocationChanged: (String) -> Unit,
    onRepeatRuleChanged: (RepeatRuleInfo?) -> Unit,
    onSaveScheduleClicked: (UpdateType) -> Unit,
    onCancelClick: () -> Unit,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                ScheduleCreateContract.Effect.NavigateBack -> backDispatcher?.onBackPressed()
            }
        }
    }

    ScheduleCreateScreen(
        modifier = modifier,
        schedule = uiState.schedule,
        repeatRule = uiState.repeatRule,
        onTitleChanged = onTitleChanged,
        onContentChanged = onContentChanged,
        onRepeatRuleChanged = onRepeatRuleChanged,
        onStartDateChanged = onStartDateChanged,
        onEndDateChanged = onEndDateChanged,
        onLocationChanged = onLocationChanged,
        onSaveScheduleClicked = onSaveScheduleClicked,
        onCancelClick = onCancelClick
    )

    if (uiState.isOnCreate) LoadingDialog()
}

@Composable
fun ScheduleCreateScreen(
    modifier: Modifier = Modifier,
    schedule: ScheduleInfo,
    repeatRule: RepeatRuleInfo?,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onRepeatRuleChanged: (RepeatRuleInfo?) -> Unit,
    onStartDateChanged: (LocalDateTime) -> Unit,
    onEndDateChanged: (LocalDateTime) -> Unit,
    onSaveScheduleClicked: (UpdateType) -> Unit,
    onCancelClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        CalendarTopAppBar()

        ScheduleInputForm(
            modifier = Modifier.weight(1f),
            schedule = schedule,
            repeatRule = repeatRule,
            onTitleChanged = onTitleChanged,
            onContentChanged = onContentChanged,
            onLocationChanged = onLocationChanged,
            onRepeatRuleChanged = onRepeatRuleChanged,
            onStartDateChanged = onStartDateChanged,
            onEndDateChanged = onEndDateChanged,
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = onCancelClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "취소",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            TextButton(
                onClick = { onSaveScheduleClicked(UpdateType.ALL) },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "확인",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewScheduleEditScreen() {
    NeeGongNaeGongTheme {
        ScheduleCreateScreen(
            modifier = Modifier.fillMaxSize(),
            schedule = ScheduleInfo(
                title = "New Schedule",
                content = null,
                startAt = LocalDateTime.now(),
                endAt = LocalDateTime.now().plusHours(1),
                isAllDay = false,
                location = null,
            ),
            repeatRule = RepeatRuleInfo(
                repeatType = RepeatType.MONTHLY,
                repeatInterval = 1,
                repeatDay = 3,
                endDate = null
            ),
            onTitleChanged = { },
            onContentChanged = { },
            onLocationChanged = { },
            onRepeatRuleChanged = { },
            onStartDateChanged = { },
            onEndDateChanged = { },
            onSaveScheduleClicked = { },
            onCancelClick = { }
        )
    }
}
