package com.ssafy.neegongnaegong.presentation.calendar.component.input.repeatrule.radio

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

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
                if (string.isEmpty()) {
                    Text(
                        modifier =
                            Modifier
                                .width(IntrinsicSize.Min)
                                .padding(end = 8.dp)
                                .bottomBorder(1.dp, NeeGongNaeGongTheme.colorScheme.primaryText),
                        text = "1",
                        style = NeeGongNaeGongTheme.typography.bodyMedium.copy(color = NeeGongNaeGongTheme.colorScheme.primaryText),
                    )
                }
                BasicTextField(
                    modifier =
                        Modifier
                            .width(IntrinsicSize.Min)
                            .padding(end = 8.dp)
                            .bottomBorder(1.dp, NeeGongNaeGongTheme.colorScheme.primaryText),
                    value = string,
                    onValueChange = {
                        string = it
                    },
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                    textStyle = NeeGongNaeGongTheme.typography.bodyMedium.copy(color = NeeGongNaeGongTheme.colorScheme.primaryText),
                )
            }
        }
        Text(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
            text = type?.toDisplayString() ?: "반복 안 함",
            style = NeeGongNaeGongTheme.typography.bodyMedium,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
        )
    }
}

@Composable
fun Modifier.bottomBorder(
    strokeWidth: Dp,
    color: Color,
): Modifier {
    val density = LocalDensity.current
    val strokeWidthPx = density.run { strokeWidth.toPx() }
    return this then
        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx,
            )
        }
}

@Preview
@Composable
private fun RepeatRuleTypeRadioButtonPreview() {
}
