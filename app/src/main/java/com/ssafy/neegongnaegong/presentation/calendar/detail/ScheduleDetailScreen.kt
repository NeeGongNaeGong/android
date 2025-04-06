package com.ssafy.neegongnaegong.presentation.calendar.detail

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.presentation.calendar.component.ScheduleEditText
import com.ssafy.neegongnaegong.presentation.calendar.component.picker.DateTimeRangePicker
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ScheduleDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: ScheduleDetailViewModel = hiltViewModel(),
    scheduleId: Long,
    popBackStack: () -> Unit,
    navigateToEditScheduleScreen: (Long) -> Unit
) {
    BackHandler {
        popBackStack()
    }

    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent(ScheduleDetailContract.Event.OnLoad(scheduleId))
    }

    ScheduleDetailContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onEditClick = { viewModel.setEvent(ScheduleDetailContract.Event.OnEditClick) },
        onDeleteClick = { viewModel.setEvent(ScheduleDetailContract.Event.OnDeleteClick(it)) },
        navigateToEditScheduleScreen = { navigateToEditScheduleScreen(it.id) },
    )
}

@Composable
fun ScheduleDetailContent(
    modifier: Modifier = Modifier,
    effect: Flow<ScheduleDetailContract.Effect>,
    uiState: ScheduleDetailContract.State,
    onEditClick: () -> Unit,
    onDeleteClick: (DeleteType) -> Unit,
    navigateToEditScheduleScreen: (Schedule) -> Unit
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                ScheduleDetailContract.Effect.NavigateBack -> backDispatcher?.onBackPressed()
                is ScheduleDetailContract.Effect.ShowErrorSnackBar -> scope.launch {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = "확인",
                        duration = SnackbarDuration.Short
                    )
                }

                is ScheduleDetailContract.Effect.NavigateToEditScheduleScreen -> {
                    navigateToEditScheduleScreen(effect.schedule)
                }
            }
        }
    }

    if (uiState.isLoading || uiState.isOnDelete) LoadingDialog()

    if (uiState.schedule != null) ScheduleDetailScreen(
        modifier = modifier,
        title = uiState.schedule.info.title,
        content = uiState.schedule.info.content,
        startDate = uiState.schedule.info.startDate,
        endDate = uiState.schedule.info.endDate,
        isAllDay = uiState.schedule.info.isAllDay,
        location = uiState.schedule.info.location,
        repeatRule = uiState.schedule.info.repeatRule?.info,
        onEditClick = onEditClick,
        onDeleteClick = onDeleteClick,
    )
}

@Composable
fun ScheduleDetailScreen(
    modifier: Modifier = Modifier,
    title: String,
    content: String? = null,
    startDate: LocalDateTime,
    endDate: LocalDateTime,
    isAllDay: Boolean,
    location: String?,
    repeatRule: RepeatRuleInfo?,
    onEditClick: () -> Unit,
    onDeleteClick: (DeleteType) -> Unit,
) {
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
                onTextChange = {},
                placeHolder = "제목",
                enabled = false
            )
            DateTimeRangePicker(
                startDateTime = startDate,
                endDateTime = endDate,
                isAllDay = isAllDay,
                onStartDateTimeChange = {},
                onEndDateTimeChange = {},
                onIsAllDayToggle = {},
                enable = false,
            )
            if (content != null) ScheduleEditText(
                modifier = Modifier.fillMaxWidth(),
                text = content,
                onTextChange = {},
                placeHolder = "메모",
                prefix = Icons.Outlined.Description,
                enabled = false
            )
            if (location != null) ScheduleEditText(
                modifier = Modifier.fillMaxWidth(),
                text = location,
                onTextChange = {},
                placeHolder = "장소",
                prefix = Icons.Outlined.LocationOn,
                enabled = false
            )
            ScheduleEditText(
                modifier = Modifier.fillMaxWidth(),
                text = repeatRule?.toDisplayString() ?: "반복 안 함",
                placeHolder = "반복 안 함",
                prefix = Icons.Outlined.Repeat,
                enabled = false,
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(
                onClick = { onDeleteClick(DeleteType.ALL) }, // TODO: 추후 수정
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "삭제",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            TextButton(
                onClick = onEditClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "수정",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScheduleDetailScreen() {
    NeeGongNaeGongTheme(dynamicColor = false) {
        Surface {
            ScheduleDetailScreen(
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
                onEditClick = {},
                onDeleteClick = {},
            )
        }
    }
}
