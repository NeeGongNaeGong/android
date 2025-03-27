package com.ssafy.neegongnaegong.presentation.calendar.component

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.presentation.calendar.component.picker.DateRangePicker
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDate
import java.time.YearMonth
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
    onClick: (Int) -> Unit
) {
    var interval by remember { mutableIntStateOf(1) }

    RepeatRuleRadioButton(
        modifier = modifier,
        selected = selected,
        onClick = { onClick(interval) },
    ) {
        if (type != null) BasicTextField(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(end = 8.dp)
                .bottomBorder(1.dp, MaterialTheme.colorScheme.onBackground),
            value = interval.toString(),
            onValueChange = {
                interval = it.toIntOrNull() ?: 1
                onClick(interval)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
        )
        Text(
            modifier = Modifier.weight(1f).padding(end = 16.dp),
            text = type?.toDisplayString() ?: "반복 안 함",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun RepeatRuleEndDateInput(
    modifier: Modifier = Modifier,
    endDate: LocalDate?,
    onEndDateChange: (LocalDate?) -> Unit = {}
) {
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Switch(
                modifier = Modifier
                    .scale(0.75f)
                    .padding(end = 16.dp),
                checked = hasEndDate,
                onCheckedChange = { hasEndDate = it },
                colors = SwitchDefaults.colors().copy(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    checkedBorderColor = MaterialTheme.colorScheme.primary,
                    checkedIconColor = MaterialTheme.colorScheme.onPrimary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onBackground,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    uncheckedBorderColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
        AnimatedVisibility(visible = hasEndDate) {
            DateRangePicker(
                modifier = Modifier.fillMaxWidth(),
                initialDate = endDate,
                initialMonth = endDate?.let { YearMonth.from(it) } ?: YearMonth.now(),
                onDateSelected = onEndDateChange,
                startDate = endDate,
                endDate = endDate,
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
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        content()
    }
}

@Preview
@Composable
fun RepeatRuleRadioButtonPreview(modifier: Modifier = Modifier) {
    NeeGongNaeGongTheme(dynamicColor = false) {
        Surface {
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