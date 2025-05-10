package com.ssafy.neegongnaegong.presentation.calendar.component.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.presentation.calendar.component.RepeatRuleInput
import com.ssafy.neegongnaegong.presentation.calendar.component.ScheduleEditText
import com.ssafy.neegongnaegong.presentation.component.picker.datetime.range.DateTimeRangePicker
import com.ssafy.neegongnaegong.presentation.component.picker.datetime.range.rememberDateTimeRangePickerState
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime

@Composable
fun ScheduleInputForm(
    modifier: Modifier = Modifier,
    schedule: ScheduleInfo,
    repeatRule: RepeatRuleInfo?,
    initialFocus: ScheduleInputFormFocus = ScheduleInputFormFocus.None,
    onTitleChanged: (String) -> Unit,
    onStartDateChanged: (LocalDateTime) -> Unit,
    onEndDateChanged: (LocalDateTime) -> Unit,
    onContentChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onRepeatRuleChanged: (RepeatRuleInfo?) -> Unit, enable: Boolean = true,
) {
    val titleFocus = remember { FocusRequester() }
    val contentFocus = remember { FocusRequester() }
    val locationFocus = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    var isRepeatRuleFocused by remember { mutableStateOf(false) }

    val dateTimeRangePickerState = rememberDateTimeRangePickerState(
        startDateTime = schedule.startAt,
        endDateTime = schedule.endAt,
        isAllDay = schedule.isAllDay
    )

    LaunchedEffect(schedule.startAt) {
        dateTimeRangePickerState.updateStartDateTime(schedule.startAt)
    }

    LaunchedEffect(schedule.endAt) {
        dateTimeRangePickerState.updateEndDateTime(schedule.endAt)
    }

    LaunchedEffect(schedule.isAllDay) {
        dateTimeRangePickerState.updateIsAllDay(schedule.isAllDay)
    }

    LaunchedEffect(initialFocus) {
        focusManager.clearFocus()
        when (initialFocus) {
            ScheduleInputFormFocus.None -> {}
            ScheduleInputFormFocus.Title -> titleFocus.requestFocus()
            ScheduleInputFormFocus.StartDate -> dateTimeRangePickerState.focusOnStartDate()
            ScheduleInputFormFocus.StartTime -> dateTimeRangePickerState.focusOnStartTime()
            ScheduleInputFormFocus.EndDate -> dateTimeRangePickerState.focusOnEndDate()
            ScheduleInputFormFocus.EndTime -> dateTimeRangePickerState.focusOnEndTime()
            ScheduleInputFormFocus.IsAllDay -> dateTimeRangePickerState.toggleIsAllDay()
            ScheduleInputFormFocus.Content -> contentFocus.requestFocus()
            ScheduleInputFormFocus.Location -> locationFocus.requestFocus()
            ScheduleInputFormFocus.RepeatRule -> isRepeatRuleFocused = true
        }
    }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ScheduleEditText(
            modifier = Modifier
                .focusRequester(titleFocus)
                .fillMaxWidth(),
            text = schedule.title,
            onTextChange = onTitleChanged,
            placeHolder = "제목",
            enable = enable,
        )
        DateTimeRangePicker(
            state = dateTimeRangePickerState,
            onStartDateTimeChange = onStartDateChanged,
            onEndDateTimeChange = onEndDateChanged,
            enable = enable,
        )
        ScheduleEditText(
            modifier = Modifier
                .focusRequester(contentFocus)
                .fillMaxWidth(),
            text = schedule.content ?: "",
            onTextChange = onContentChanged,
            placeHolder = "메모",
            prefix = Icons.Outlined.Description,
            enable = enable,
        )
        ScheduleEditText(
            modifier = Modifier
                .focusRequester(locationFocus)
                .fillMaxWidth(),
            text = schedule.location ?: "",
            onTextChange = onLocationChanged,
            placeHolder = "장소",
            prefix = Icons.Outlined.LocationOn,
            enable = enable,
        )
        ScheduleEditText(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isRepeatRuleFocused = !isRepeatRuleFocused },
            text = repeatRule?.toDisplayString() ?: "반복 안 함",
            placeHolder = "반복 안 함",
            prefix = Icons.Outlined.Repeat,
            enable = false,
        )
        AnimatedVisibility(enable && isRepeatRuleFocused) {
            RepeatRuleInput(
                repeatRule = repeatRule,
                onRepeatRuleChange = onRepeatRuleChanged
            )
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
private fun ScheduleInputFormPreview() {
    NeeGongNaeGongTheme {
        ScheduleInputForm(
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
            onTitleChanged = {},
            onStartDateChanged = {},
            onEndDateChanged = {},
            onContentChanged = {},
            onLocationChanged = {},
            onRepeatRuleChanged = {},
            enable = true,
        )
    }
}

enum class ScheduleInputFormFocus {
    None,
    Title,
    StartDate,
    StartTime,
    EndDate,
    EndTime,
    IsAllDay,
    Content,
    Location,
    RepeatRule
}
