package com.ssafy.neegongnaegong.presentation.group.vote

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.presentation.group.vote.component.OptionButton
import com.ssafy.neegongnaegong.presentation.group.vote.component.TimePickerDialog
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.TimeFormatter
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest


@Composable
fun VoteRoute(
    popBackStack: () -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: VoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            VoteTopBar(
                popBackStack,
                onClickCompleteButton = { viewModel.setEvent(VoteContract.Event.OnClickCompleteButton) })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        LaunchedEffect(viewModel.effect) {
            viewModel.effect.collectLatest { effect ->
                when (effect) {
                    is VoteContract.Effect.ShowToast -> {
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            actionLabel = "확인",
                            duration = SnackbarDuration.Short
                        )
                    }

                    VoteContract.Effect.NavigateToBackStack -> {
                        popBackStack()
                    }
                }
            }
        }

        VoteContent(
            modifier
                .fillMaxSize()
                .padding(paddingValues),
            uiState,
            onClickAddVoteItemButton = { viewModel.setEvent(VoteContract.Event.OnClickAddVoteItemButton) },
            onClickMultipleSelectionOption = { viewModel.setEvent(VoteContract.Event.OnClickMultipleSelectionOption) },
            onClickAnonymousVotingOption = { viewModel.setEvent(VoteContract.Event.OnClickAnonymousVotingOption) },
            onClickAllowAddingSelectionOption = { viewModel.setEvent(VoteContract.Event.OnClickAllowAddingSelectionOption) },
            onClickEndDateOption = { viewModel.setEvent(VoteContract.Event.OnClickEndDateOption) },
            onClickAlarmBeforeClosingOption = { viewModel.setEvent(VoteContract.Event.OnClickAlarmBeforeClosingOption) },
            onVoteTitleChanged = { title ->
                viewModel.setEvent(
                    VoteContract.Event.OnVoteTitleChanged(
                        title
                    )
                )
            },
            onVoteItemChanged = { index, title ->
                viewModel.setEvent(
                    VoteContract.Event.OnVoteItemChanged(
                        index,
                        title
                    )
                )
            },
            onClickDateButton = { viewModel.setEvent(VoteContract.Event.OnClickDateButton) },
            onClickTimeButton = { viewModel.setEvent(VoteContract.Event.OnClickTimeButton) },
            onDismissDateButton = { viewModel.setEvent(VoteContract.Event.OnDismissDateButton) },
            onDismissTimeButton = { viewModel.setEvent(VoteContract.Event.OnDismissTimeButton) },
            onChangeDate = { date -> viewModel.setEvent(VoteContract.Event.OnChangeDate(date)) },
            onChangeTime = { hour, minute ->
                viewModel.setEvent(
                    VoteContract.Event.OnChangeTime(
                        hour,
                        minute
                    )
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoteTopBar(
    popBackStack: () -> Boolean,
    onClickCompleteButton: () -> Unit
) {
    // title을 가운데로 위치시켜주는 CenterAlignedTopAppBar
    // https://stackoverflow.com/questions/67497414/how-to-align-title-at-layout-center-in-topappbar
    CenterAlignedTopAppBar(
        title = {
            Text(
                style = NeeGongNaeGongTheme.typography.titleSmall,
                text = "투표 만들기"
            )
        },
        navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로 가기 버튼"
                )
            }
        },
        actions = {
            TextButton(onClick = { popBackStack() }) {
                Text(
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    text = "완료"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = NeeGongNaeGongTheme.colorScheme.background,
            titleContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
            navigationIconContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
            actionIconContentColor = NeeGongNaeGongTheme.colorScheme.primaryText,
        )
    )
}

@Composable
fun VoteContent(
    modifier: Modifier = Modifier,
    uiState: VoteContract.State,
    onClickAddVoteItemButton: () -> Unit,
    onClickMultipleSelectionOption: () -> Unit,
    onClickAnonymousVotingOption: () -> Unit,
    onClickAllowAddingSelectionOption: () -> Unit,
    onClickEndDateOption: () -> Unit,
    onClickAlarmBeforeClosingOption: () -> Unit,
    onVoteTitleChanged: (String) -> Unit,
    onVoteItemChanged: (Int, String) -> Unit,

    onClickDateButton: () -> Unit,
    onClickTimeButton: () -> Unit,
    onDismissDateButton: () -> Unit,
    onDismissTimeButton: () -> Unit,

    onChangeDate: (Long) -> Unit,
    onChangeTime: (Int, Int) -> Unit
) {
    LoadDialog(
        uiState.isDateDialogVisible,
        uiState.isTimeDialogVisible,
        uiState.date,
        uiState.time,
        onDismissDateButton,
        onDismissTimeButton,
        onChangeDate,
        onChangeTime
    )

    VoteScreen(
        modifier, uiState,
        onClickAddVoteItemButton,
        onClickMultipleSelectionOption,
        onClickAnonymousVotingOption,
        onClickAllowAddingSelectionOption,
        onClickEndDateOption,
        onClickAlarmBeforeClosingOption,
        onVoteTitleChanged,
        onVoteItemChanged,
        onClickDateButton,
        onClickTimeButton
    )
}

@Composable
fun VoteScreen(
    modifier: Modifier = Modifier,
    uiState: VoteContract.State,
    onClickAddVoteItemButton: () -> Unit,
    onClickMultipleSelectionOption: () -> Unit,
    onClickAnonymousVotingOption: () -> Unit,
    onClickAllowAddingSelectionOption: () -> Unit,
    onClickEndDateOption: () -> Unit,
    onClickAlarmBeforeClosingOption: () -> Unit,
    onVoteTitleChanged: (String) -> Unit,
    onVoteItemChanged: (Int, String) -> Unit,
    onClickDateButton: () -> Unit,
    onClickTimeButton: () -> Unit,
) {
    LazyColumn(
        modifier
            .fillMaxSize()
            .background(NeeGongNaeGongTheme.colorScheme.background)
            .padding(horizontal = 13.dp)
    ) {
        item {
            MainOption(
                uiState.voteTitle,
                uiState.voteItemList,
                uiState.isMultipleSelectionEnabled,
                uiState.isAnonymousVotingEnabled,
                uiState.allowAddingSelection,
                onClickAddVoteItemButton,
                onClickMultipleSelectionOption,
                onClickAnonymousVotingOption,
                onClickAllowAddingSelectionOption,
                onVoteTitleChanged,
                onVoteItemChanged,
            )
        }
        item {
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
        }
        item {
            EndOption(
                uiState.isEndDateEnabled,
                uiState.isAlarmBeforeClosingEnabled,
                uiState.date,
                uiState.time,
                onClickEndDateOption,
                onClickAlarmBeforeClosingOption,
                onClickDateButton,
                onClickTimeButton,
            )
        }
    }
}

@Composable
fun MainOption(
    voteTitle: String,
    voteItemList: List<String>,
    isMultipleSelectionEnabled: Boolean,
    isAnonymousVotingEnabled: Boolean,
    allowAddingSelection: Boolean,
    onClickAddVoteItemButton: () -> Unit,
    onClickMultipleSelectionOption: () -> Unit,
    onClickAnonymousVotingOption: () -> Unit,
    onClickAllowAddingSelectionOption: () -> Unit,
    onVoteTitleChanged: (String) -> Unit,
    onVoteItemChanged: (Int, String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(NeeGongNaeGongTheme.colorScheme.gray2)
            .padding(13.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        VoteList(
            voteTitle,
            voteItemList,
            onClickAddVoteItemButton,
            onVoteTitleChanged,
            onVoteItemChanged,
        )
        VoteOption(
            isMultipleSelectionEnabled,
            isAnonymousVotingEnabled,
            allowAddingSelection,
            onClickMultipleSelectionOption,
            onClickAnonymousVotingOption,
            onClickAllowAddingSelectionOption,
        )
    }
}

@Composable
fun VoteList(
    voteTitle: String,
    voteItemList: List<String>,
    onClickAddVoteItemButton: () -> Unit,
    onVoteTitleChanged: (String) -> Unit,
    onVoteItemChanged: (Int, String) -> Unit,
) {
    val textFieldModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(15.dp))
        .background(NeeGongNaeGongTheme.colorScheme.background)

    val textFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = NeeGongNaeGongTheme.colorScheme.background,
        unfocusedContainerColor = NeeGongNaeGongTheme.colorScheme.background,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
        focusedTextColor = NeeGongNaeGongTheme.colorScheme.primaryText,
    )

    TextField(
        modifier = textFieldModifier,
        colors = textFieldColors,
        singleLine = true,
        value = voteTitle,
        onValueChange = { title -> onVoteTitleChanged(title) },
        placeholder = { Text("투표 제목") }
    )

    voteItemList.mapIndexed { index, itemTitle ->
        TextField(
            modifier = textFieldModifier,
            colors = textFieldColors,
            singleLine = true,
            value = itemTitle,
            onValueChange = { title -> onVoteItemChanged(index, title) },
            placeholder = { Text("항목 입력") }
        )
    }

    IconButton(
        onClick = { onClickAddVoteItemButton() },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(NeeGongNaeGongTheme.colorScheme.background)
    ) {
        Icon(
            Icons.Rounded.Add,
            modifier = Modifier.size(48.dp),
            contentDescription = "항목 추가",
            tint = NeeGongNaeGongTheme.colorScheme.gray4
        )
    }
}

@Composable
fun VoteOption(
    isMultipleSelectionEnabled: Boolean,
    isAnonymousVotingEnabled: Boolean,
    allowAddingSelection: Boolean,
    onClickMultipleSelectionOption: () -> Unit,
    onClickAnonymousVotingOption: () -> Unit,
    onClickAllowAddingSelectionOption: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(NeeGongNaeGongTheme.colorScheme.gray2)
    ) {
        OptionButton(isMultipleSelectionEnabled, "복수선택") { onClickMultipleSelectionOption() }
        OptionButton(isAnonymousVotingEnabled, "익명투표") { onClickAnonymousVotingOption() }
        OptionButton(allowAddingSelection, "선택 항목 추가 허용") { onClickAllowAddingSelectionOption() }
    }
}

@Composable
fun EndOption(
    isEndDateEnabled: Boolean,
    isAlarmBeforeClosingEnabled: Boolean,
    date: String,
    time: String,
    onClickEndDateOption: () -> Unit,
    onClickAlarmBeforeClosingOption: () -> Unit,
    onClickDatePicker: () -> Unit,
    onClickTimePicker: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(NeeGongNaeGongTheme.colorScheme.gray2)
            .padding(13.dp)
    ) {
        OptionButton(isEndDateEnabled, "종료 시간") { onClickEndDateOption() }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(NeeGongNaeGongTheme.colorScheme.background)
                    .padding(vertical = 2.dp, horizontal = 5.dp),
                onClick = onClickDatePicker
            ) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    style = NeeGongNaeGongTheme.typography.labelMedium,
                    text = date
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 10.dp))

            TextButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(NeeGongNaeGongTheme.colorScheme.background)
                    .padding(vertical = 2.dp, horizontal = 5.dp),
                onClick = onClickTimePicker
            ) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    style = NeeGongNaeGongTheme.typography.labelMedium,
                    text = time
                )
            }

        }
        OptionButton(
            isAlarmBeforeClosingEnabled,
            "종료 30분 전 알림"
        ) { onClickAlarmBeforeClosingOption() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadDialog(
    showDatePicker: Boolean,
    showTimePicker: Boolean,
    date: String,
    time: String,
    onDismissDateButton: () -> Unit,
    onDismissTimeButton: () -> Unit,
    onChangeDate: (Long) -> Unit,
    onChangeTime: (Int, Int) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            // 오늘 이전 날짜는 선택할 수 없도록 설정
            val today = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis


            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= today
            }

            override fun isSelectableYear(year: Int): Boolean {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                return year >= currentYear
            }
        },
        initialSelectedDateMillis = TimeFormatter.millisToUtc(
            TimeFormatter.convertStringDateToMillis(
                date
            )
        ),
    )

    val (hour, minute) = TimeFormatter.convertStringTimeToHourAndMinute(time)
    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
    )


    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = onDismissDateButton,
            confirmButton = {
                TextButton(onClick = {
                    onDismissDateButton()
                    datePickerState.selectedDateMillis?.let {
                        onChangeDate(it)
                    }
                }) { Text("확인") }
            },
            dismissButton = { TextButton(onClick = onDismissDateButton) { Text("취소") } },
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
            )
        ) {
            DatePicker(
                colors = DatePickerDefaults.colors(containerColor = Color.White),
                state = datePickerState
            )
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismiss = onDismissTimeButton,
            onConfirm = {
                onChangeTime(timePickerState.hour, timePickerState.minute)
                onDismissTimeButton()
            },
        ) {
            TimeInput(
                colors = TimePickerDefaults.colors(containerColor = Color.White),
                state = timePickerState
            )
        }
    }
}


@NeeGongNaeGongPreviews
@Composable
fun PreviewTopAppBar() {
    NeeGongNaeGongTheme {
        VoteTopBar(popBackStack = { true }, {})
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewVoteScreen() {
    NeeGongNaeGongTheme {
        VoteScreen(
            Modifier
                .fillMaxSize(),
            VoteContract.State(
                isMultipleSelectionEnabled = false,
                isAnonymousVotingEnabled = false,
                allowAddingSelection = false,
                isEndDateEnabled = false,
                isAlarmBeforeClosingEnabled = false,
                voteTitle = "",
                voteItemList = persistentListOf("", "", ""),
                "",
                "",
                isDateDialogVisible = false,
                isTimeDialogVisible = false
            ),
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            { idx, title -> },
            onClickDateButton = {},
            onClickTimeButton = {},
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewMainOption() {
    NeeGongNaeGongTheme {
        MainOption(
            "uiState.voteTitle",
            listOf(),
            false,
            false,
            false,
            { },
            { },
            {},
            { },
            { },
            { idx, title -> },
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewEndOption() {
    NeeGongNaeGongTheme {
        EndOption(
            false,
            false,
            onClickEndDateOption = {},
            onClickAlarmBeforeClosingOption = {},
            onClickDatePicker = {},
            onClickTimePicker = {},
            date = "1",
            time = "1"
        )
    }
}