package com.ssafy.neegongnaegong.presentation.calendar.edit

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import com.ssafy.neegongnaegong.presentation.calendar.component.CalendarTopAppBar
import com.ssafy.neegongnaegong.presentation.calendar.component.dialog.UpdateTypeSelectDialog
import com.ssafy.neegongnaegong.presentation.calendar.component.form.ScheduleInputForm
import com.ssafy.neegongnaegong.presentation.calendar.component.form.ScheduleInputFormFocus
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun ScheduleEditRoute(
    modifier: Modifier = Modifier,
    scheduleId: Long,
    date: LocalDate,
    initialFocus: ScheduleInputFormFocus,
    viewModel: ScheduleEditViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    BackHandler {
        popBackStack()
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(ScheduleEditContract.Event.OnLoad(scheduleId, date))
    }

    val uiState = viewModel.uiState.collectAsState()

    ScheduleEditContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        initialFocus = initialFocus,
        onTitleChanged = { viewModel.setEvent(ScheduleEditContract.Event.OnTitleChanged(it)) },
        onContentChanged = { viewModel.setEvent(ScheduleEditContract.Event.OnContentChanged(it)) },
        onStartDateChanged = { viewModel.setEvent(ScheduleEditContract.Event.OnStartAtChanged(it)) },
        onEndDateChanged = { viewModel.setEvent(ScheduleEditContract.Event.OnEndAtChanged(it)) },
        onLocationChanged = { viewModel.setEvent(ScheduleEditContract.Event.OnLocationChanged(it)) },
        onRepeatRuleChanged = { viewModel.setEvent(ScheduleEditContract.Event.OnRepeatRuleChanged(it)) },
        onUpdateTypeSelected = {
            viewModel.setEvent(
                ScheduleEditContract.Event.OnUpdateTypeSelected(
                    it,
                ),
            )
        },
        onSaveScheduleClicked = { viewModel.setEvent(ScheduleEditContract.Event.OnSaveScheduleClicked) },
        onCancelClick = { viewModel.setEvent(ScheduleEditContract.Event.OnCancelClick) },
        onDialogDismissed = { viewModel.setEvent(ScheduleEditContract.Event.OnDialogDismissed) },
    )
}

@Composable
fun ScheduleEditContent(
    modifier: Modifier = Modifier,
    effect: Flow<ScheduleEditContract.Effect>,
    uiState: ScheduleEditContract.State,
    initialFocus: ScheduleInputFormFocus,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onStartDateChanged: (LocalDateTime) -> Unit,
    onEndDateChanged: (LocalDateTime) -> Unit,
    onLocationChanged: (String) -> Unit,
    onRepeatRuleChanged: (RepeatRuleInfo?) -> Unit,
    onSaveScheduleClicked: () -> Unit,
    onUpdateTypeSelected: (UpdateType) -> Unit,
    onDialogDismissed: () -> Unit,
    onCancelClick: () -> Unit,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                ScheduleEditContract.Effect.NavigateBack -> backDispatcher?.onBackPressed()
            }
        }
    }

    // TODO : 데이터를 받아오기 전에 로직이 돌아가서 상태관리 이상함 수정 필요
    if (!uiState.isLoading) {
        ScheduleEditScreen(
            modifier = modifier,
            initialFocus = initialFocus,
            schedule = uiState.schedule,
            repeatRule = uiState.repeatRule,
            onTitleChanged = onTitleChanged,
            onContentChanged = onContentChanged,
            onRepeatRuleChanged = onRepeatRuleChanged,
            onStartDateChanged = onStartDateChanged,
            onEndDateChanged = onEndDateChanged,
            onLocationChanged = onLocationChanged,
            onSaveScheduleClicked = onSaveScheduleClicked,
            onCancelClick = onCancelClick,
        )
    }

    if (uiState.isLoading || uiState.isOnSave) LoadingDialog()

    if (uiState.isUpdateTypeSelectorShow && lifecycleState.isAtLeast(Lifecycle.State.STARTED)) {
        UpdateTypeSelectDialog(
            repeatType = uiState.initSchedule.info.repeatRule?.info?.repeatType,
            onDismissRequest = onDialogDismissed,
            onUpdateTypeSelected = onUpdateTypeSelected,
        )
    }
}

@Composable
fun ScheduleEditScreen(
    modifier: Modifier = Modifier,
    schedule: ScheduleInfo,
    repeatRule: RepeatRuleInfo?,
    initialFocus: ScheduleInputFormFocus,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onRepeatRuleChanged: (RepeatRuleInfo?) -> Unit,
    onStartDateChanged: (LocalDateTime) -> Unit,
    onEndDateChanged: (LocalDateTime) -> Unit,
    onSaveScheduleClicked: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .then(modifier),
    ) {
        CalendarTopAppBar()

        ScheduleInputForm(
            modifier = Modifier.weight(1f),
            schedule = schedule,
            repeatRule = repeatRule,
            initialFocus = initialFocus,
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
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    "취소",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }
            TextButton(
                onClick = onSaveScheduleClicked,
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    "확인",
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun PreviewScheduleEditScreen() {
    NeeGongNaeGongTheme {
        ScheduleEditScreen(
            modifier = Modifier.fillMaxSize(),
            initialFocus = ScheduleInputFormFocus.None,
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
            onTitleChanged = { },
            onContentChanged = { },
            onLocationChanged = { },
            onRepeatRuleChanged = { },
            onStartDateChanged = { },
            onEndDateChanged = { },
            onSaveScheduleClicked = { },
            onCancelClick = { },
        )
    }
}
