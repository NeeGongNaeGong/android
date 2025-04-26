package com.ssafy.neegongnaegong.presentation.calendar.create

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import com.ssafy.neegongnaegong.presentation.calendar.component.CalendarTopAppBar
import com.ssafy.neegongnaegong.presentation.calendar.component.RepeatRuleInput
import com.ssafy.neegongnaegong.presentation.calendar.component.ScheduleEditText
import com.ssafy.neegongnaegong.presentation.calendar.component.picker.DateTimeRangePicker
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        onIsAllDayChanged = { viewModel.setEvent(ScheduleCreateContract.Event.OnIsAllDayChanged(it)) },
        onLocationChanged = { viewModel.setEvent(ScheduleCreateContract.Event.OnLocationChanged(it)) },
        onRepeatRuleChanged = {
            viewModel.setEvent(
                ScheduleCreateContract.Event.OnRepeatRuleChanged(
                    it
                )
            )
        },
        onSaveScheduleClicked = {
            viewModel.setEvent(
                ScheduleCreateContract.Event.OnCreateScheduleClicked
            )
        },
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
    onIsAllDayChanged: (Boolean) -> Unit,
    onLocationChanged: (String) -> Unit,
    onRepeatRuleChanged: (RepeatRuleInfo?) -> Unit,
    onSaveScheduleClicked: (UpdateType) -> Unit,
    onCancelClick: () -> Unit,
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                ScheduleCreateContract.Effect.NavigateBack -> backDispatcher?.onBackPressed()
                is ScheduleCreateContract.Effect.ShowErrorSnackBar -> scope.launch {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = "확인",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = { CalendarTopAppBar() }
    ) { innerPadding ->
        ScheduleCreateScreen(
            modifier = modifier.padding(innerPadding),
            title = uiState.schedule.title,
            content = uiState.schedule.content,
            startDate = uiState.schedule.startDate,
            endDate = uiState.schedule.endDate,
            isAllDay = uiState.schedule.isAllDay,
            location = uiState.schedule.location,
            repeatRule = uiState.repeatRule,
            onTitleChange = onTitleChanged,
            onContentChange = onContentChanged,
            onRepeatRuleChanged = onRepeatRuleChanged,
            onStartDateChange = onStartDateChanged,
            onEndDateChange = onEndDateChanged,
            onIsAllDayChange = onIsAllDayChanged,
            onLocationChange = onLocationChanged,
            onSaveScheduleClicked = onSaveScheduleClicked,
            onCancelClick = onCancelClick
        )
    }

    if (uiState.isOnCreate) LoadingDialog()
}

@Composable
fun ScheduleCreateScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: String? = null,
    startDate: LocalDateTime,
    endDate: LocalDateTime,
    isAllDay: Boolean,
    location: String?,
    repeatRule: RepeatRuleInfo?,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onRepeatRuleChanged: (RepeatRuleInfo?) -> Unit,
    onStartDateChange: (LocalDateTime) -> Unit,
    onEndDateChange: (LocalDateTime) -> Unit,
    onIsAllDayChange: (Boolean) -> Unit,
    onSaveScheduleClicked: (UpdateType) -> Unit,
    onCancelClick: () -> Unit,
) {
    var isRepeatRuleFocused by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            ScheduleEditText(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                onTextChange = onTitleChange,
                placeHolder = "제목"
            )
            DateTimeRangePicker(
                startDateTime = startDate,
                endDateTime = endDate,
                isAllDay = isAllDay,
                onStartDateTimeChange = onStartDateChange,
                onEndDateTimeChange = onEndDateChange,
                onIsAllDayToggle = onIsAllDayChange,
            )
            ScheduleEditText(
                modifier = Modifier.fillMaxWidth(),
                text = content ?: "",
                onTextChange = onContentChange,
                placeHolder = "메모",
                prefix = Icons.Outlined.Description,
            )
            ScheduleEditText(
                modifier = Modifier.fillMaxWidth(),
                text = location ?: "",
                onTextChange = onLocationChange,
                placeHolder = "장소",
                prefix = Icons.Outlined.LocationOn,
            )
            ScheduleEditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isRepeatRuleFocused = !isRepeatRuleFocused },
                text = repeatRule?.toDisplayString() ?: "반복 안 함",
                placeHolder = "반복 안 함",
                prefix = Icons.Outlined.Repeat,
                enabled = false,
            )
            AnimatedVisibility(isRepeatRuleFocused) {
                RepeatRuleInput(
                    repeatRule = repeatRule,
                    onRepeatRuleChange = onRepeatRuleChanged
                )
            }
        }

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

@Preview
@Composable
private fun PreviewScheduleEditScreen() {
    NeeGongNaeGongTheme(dynamicColor = false) {
        Surface {
            ScheduleCreateScreen(
                modifier = Modifier.fillMaxSize(),
                title = "New Schedule",
                content = null,
                startDate = LocalDateTime.now(),
                endDate = LocalDateTime.now().plusHours(1),
                isAllDay = false,
                location = null,
                repeatRule = RepeatRuleInfo(
                    repeatType = RepeatType.MONTHLY,
                    repeatInterval = 1,
                    repeatDay = 3,
                    endDate = null
                ),
                onTitleChange = { },
                onContentChange = { },
                onLocationChange = { },
                onRepeatRuleChanged = { },
                onStartDateChange = { },
                onEndDateChange = { },
                onIsAllDayChange = { },
                onSaveScheduleClicked = { },
                onCancelClick = { }
            )
        }
    }
}