package com.ssafy.neegongnaegong.presentation.calendar.component.input.repeatrule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.addRepeatDay
import com.ssafy.neegongnaegong.presentation.calendar.component.input.repeatrule.radio.RepeatRuleTypeRadioButton
import com.ssafy.neegongnaegong.presentation.calendar.component.switchColors
import com.ssafy.neegongnaegong.presentation.component.picker.date.DatePicker
import com.ssafy.neegongnaegong.presentation.component.picker.date.rememberDatePickerState
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun RepeatRuleInput(
    modifier: Modifier = Modifier,
    schedule: ScheduleInfo,
    repeatRule: RepeatRuleInfo?,
    onRepeatRuleChange: (RepeatRuleInfo?) -> Unit,
) {
    Column(modifier = modifier) {
        RepeatRuleTypeRadioButton(
            type = null,
            selected = repeatRule == null,
            onClick = { _ -> onRepeatRuleChange(null) }
        )
        RepeatRuleTypeRadioButton(
            type = RepeatType.DAILY,
            selected = repeatRule?.repeatType == RepeatType.DAILY,
            initialInterval = if (repeatRule?.repeatType == RepeatType.DAILY) repeatRule.repeatInterval else 1,
            onClick = { interval ->
                onRepeatRuleChange(
                    RepeatRuleInfo(
                        repeatType = RepeatType.DAILY,
                        repeatInterval = interval,
                        repeatDay = 1,
                        endDate = repeatRule?.endDate
                    )
                )
            }
        )
        RepeatRuleTypeRadioButton(
            type = RepeatType.WEEKLY,
            selected = repeatRule?.repeatType == RepeatType.WEEKLY,
            initialInterval = if (repeatRule?.repeatType == RepeatType.WEEKLY) repeatRule.repeatInterval else 1,
            onClick = { interval ->
                onRepeatRuleChange(
                    RepeatRuleInfo(
                        repeatType = RepeatType.WEEKLY,
                        repeatInterval = interval,
                        repeatDay = (0).addRepeatDay(schedule.startAt.dayOfWeek.value),
                        endDate = repeatRule?.endDate
                    )
                )
            }
        )
        AnimatedVisibility(repeatRule?.repeatType == RepeatType.WEEKLY) {
            RepeatDayWeeklySelector(
                repeatDay = repeatRule?.repeatDay ?: 0,
                onChange = { repeatDay ->
                    onRepeatRuleChange(
                        repeatRule?.copy(
                            repeatDay = repeatDay
                        )
                    )
                }
            )
        }
        RepeatRuleTypeRadioButton(
            type = RepeatType.MONTHLY,
            selected = repeatRule?.repeatType == RepeatType.MONTHLY,
            initialInterval = if (repeatRule?.repeatType == RepeatType.MONTHLY) repeatRule.repeatInterval else 1,
            onClick = { interval ->
                onRepeatRuleChange(
                    RepeatRuleInfo(
                        repeatType = RepeatType.MONTHLY,
                        repeatInterval = interval,
                        repeatDay = (0).addRepeatDay(schedule.startAt.dayOfMonth),
                        endDate = repeatRule?.endDate
                    )
                )
            }
        )
        AnimatedVisibility(repeatRule?.repeatType == RepeatType.MONTHLY) {
            RepeatRuleMonthlySelector(
                repeatDay = repeatRule?.repeatDay ?: 0,
                onChange = { repeatDay ->
                    onRepeatRuleChange(
                        repeatRule?.copy(
                            repeatDay = repeatDay
                        )
                    )
                }
            )
        }
        AnimatedVisibility(repeatRule != null) {
            RepeatRuleEndDateInput(
                endDate = repeatRule?.endDate,
                onEndDateChange = { onRepeatRuleChange(repeatRule?.copy(endDate = it)) }
            )
        }
    }
}

@Composable
fun RepeatRuleEndDateInput(
    modifier: Modifier = Modifier,
    endDate: LocalDate?,
    onEndDateChange: (LocalDate?) -> Unit = {}
) {
    val datePickerState = rememberDatePickerState(initialDate = endDate ?: LocalDate.now())
    var hasEndDate by remember { mutableStateOf(endDate != null) }

    LaunchedEffect(hasEndDate) {
        if (!hasEndDate) onEndDateChange(null)
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = endDate?.let {
                    DateTimeFormatter.ofPattern("yyyy년 M월 d일(E)까지", Locale.KOREAN).format(it)
                } ?: "종료 날짜",
                style = NeeGongNaeGongTheme.typography.bodyMedium,
                color = NeeGongNaeGongTheme.colorScheme.primaryText
            )
            Switch(
                modifier = Modifier
                    .scale(0.75f)
                    .padding(end = 16.dp),
                checked = hasEndDate,
                onCheckedChange = { hasEndDate = it },
                colors = NeeGongNaeGongTheme.switchColors()
            )
        }
        AnimatedVisibility(visible = hasEndDate) {
            DatePicker(
                modifier = Modifier.fillMaxWidth(),
                state = datePickerState,
                onDateSelected = onEndDateChange,
            )
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun RepeatRuleInputPreview_DALIY() {
    NeeGongNaeGongTheme {
        RepeatRuleInput(
            schedule = ScheduleInfo.empty(),
            repeatRule = RepeatRuleInfo(
                repeatType = RepeatType.DAILY,
                repeatInterval = 1,
                repeatDay = 1,
                endDate = null,
            ),
            onRepeatRuleChange = {}
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun RepeatRuleInputPreview_WEEKLY() {
    NeeGongNaeGongTheme {
        RepeatRuleInput(
            schedule = ScheduleInfo.empty(),
            repeatRule = RepeatRuleInfo(
                repeatType = RepeatType.WEEKLY,
                repeatInterval = 1,
                repeatDay = 2,
                endDate = null,
            ),
            onRepeatRuleChange = {}
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun RepeatRuleInputPreview_MONTHLY() {
    NeeGongNaeGongTheme {
        RepeatRuleInput(
            schedule = ScheduleInfo.empty(),
            repeatRule = RepeatRuleInfo(
                repeatType = RepeatType.MONTHLY,
                repeatInterval = 1,
                repeatDay = 2,
                endDate = null,
            ),
            onRepeatRuleChange = {}
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun RepeatRuleInputPreview_EndDate() {
    NeeGongNaeGongTheme {
        RepeatRuleInput(
            schedule = ScheduleInfo.empty(),
            repeatRule = RepeatRuleInfo(
                repeatType = RepeatType.DAILY,
                repeatInterval = 1,
                repeatDay = 1,
                endDate = LocalDate.now(),
            ),
            onRepeatRuleChange = {}
        )
    }
}
