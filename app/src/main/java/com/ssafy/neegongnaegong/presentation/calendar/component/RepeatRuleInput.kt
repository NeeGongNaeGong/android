package com.ssafy.neegongnaegong.presentation.calendar.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
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
                        repeatDay = 1,
                        endDate = repeatRule?.endDate
                    )
                )
            }
        )
        RepeatRuleTypeRadioButton(
            type = RepeatType.MONTHLY,
            selected = repeatRule?.repeatType == RepeatType.MONTHLY,
            initialInterval = if (repeatRule?.repeatType == RepeatType.MONTHLY) repeatRule.repeatInterval else 1,
            onClick = { interval ->
                onRepeatRuleChange(
                    RepeatRuleInfo(
                        repeatType = RepeatType.MONTHLY,
                        repeatInterval = interval,
                        repeatDay = 1,
                        endDate = repeatRule?.endDate
                    )
                )
            }
        )
        AnimatedVisibility(repeatRule != null) {
            RepeatRuleEndDateInput(
                endDate = repeatRule?.endDate,
                onEndDateChange = { onRepeatRuleChange(repeatRule?.copy(endDate = it)) }
            )
        }
    }
}

@Composable
fun RepeatRuleTypeRadioButton(
    modifier: Modifier = Modifier,
    type: RepeatType?,
    selected: Boolean,
    initialInterval: Int = 1,
    onClick: (Int) -> Unit,
) {
    var string by remember { mutableStateOf(initialInterval.toString()) }

    LaunchedEffect(string) {
        onClick(string.toIntOrNull() ?: 1)
    }

    RepeatRuleRadioButton(
        modifier = modifier,
        selected = selected,
        onClick = { onClick(string.toIntOrNull() ?: 1) },
    ) {
        if (type != null) {
            Box {
                if (string.isEmpty()) Text(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .padding(end = 8.dp)
                        .bottomBorder(1.dp, NeeGongNaeGongTheme.colorScheme.primaryText),
                    text = "1",
                    style = NeeGongNaeGongTheme.typography.bodyMedium.copy(color = NeeGongNaeGongTheme.colorScheme.primaryText)
                )
                BasicTextField(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .padding(end = 8.dp)
                        .bottomBorder(1.dp, NeeGongNaeGongTheme.colorScheme.primaryText),
                    value = string,
                    onValueChange = {
                        string = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = NeeGongNaeGongTheme.typography.bodyMedium.copy(color = NeeGongNaeGongTheme.colorScheme.primaryText),
                )
            }
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            text = type?.toDisplayString() ?: "반복 안 함",
            style = NeeGongNaeGongTheme.typography.bodyMedium,
            color = NeeGongNaeGongTheme.colorScheme.primaryText
        )
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

@Composable
fun RepeatRuleRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            modifier = Modifier.padding(start = 2.dp),
            selected = selected,
            onClick = onClick,
            colors = RadioButtonColors(
                selectedColor = NeeGongNaeGongTheme.colorScheme.blue,
                unselectedColor = NeeGongNaeGongTheme.colorScheme.primaryText,
                disabledSelectedColor = NeeGongNaeGongTheme.colorScheme.blue,
                disabledUnselectedColor = NeeGongNaeGongTheme.colorScheme.primaryText
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        content()
    }
}

@NeeGongNaeGongPreviews
@Composable
fun RepeatRuleRadioButtonPreview(modifier: Modifier = Modifier) {
    NeeGongNaeGongTheme {
        RepeatRuleInput(
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
fun RepeatRuleRadioButtonPreview_EndDate(modifier: Modifier = Modifier) {
    NeeGongNaeGongTheme {
        RepeatRuleInput(
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

@Composable
fun Modifier.bottomBorder(strokeWidth: Dp, color: Color): Modifier {
    val density = LocalDensity.current
    val strokeWidthPx = density.run { strokeWidth.toPx() }
    return this then Modifier.drawBehind {
        val width = size.width
        val height = size.height - strokeWidthPx / 2

        drawLine(
            color = color,
            start = Offset(x = 0f, y = height),
            end = Offset(x = width, y = height),
            strokeWidth = strokeWidthPx
        )
    }
}