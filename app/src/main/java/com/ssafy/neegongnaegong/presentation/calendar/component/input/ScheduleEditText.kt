package com.ssafy.neegongnaegong.presentation.calendar.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.calendar.component.textFieldColors
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun ScheduleEditText(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    placeHolder: String? = null,
    prefix: ImageVector? = null,
    enable: Boolean = true,
) {
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = text,
                selection = TextRange(text.length),
            )
        )
    }

    LaunchedEffect(text) {
        textFieldValue = if(textFieldValue.text.isEmpty()) {
            textFieldValue.copy(
                text = text,
                selection = TextRange(text.length)
            )
        } else {
            textFieldValue.copy(
                text = text,
            )
        }
    }

    TextField(
        modifier = modifier,
        prefix = {
            prefix?.let {
                Icon(
                    it,
                    contentDescription = "Schedule",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        },
        enabled = enable,
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            onTextChange(textFieldValue.text)
        },
        textStyle = NeeGongNaeGongTheme.typography.bodyMedium,
        shape = RectangleShape,
        placeholder = {
            placeHolder?.let {
                Text(
                    text = placeHolder,
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText.copy(alpha = 0.6f)
                )
            }
        },
        colors = NeeGongNaeGongTheme.textFieldColors()
    )
}

@NeeGongNaeGongPreviews
@Composable
private fun ScheduleEditTextPreview() {
    NeeGongNaeGongTheme {
        ScheduleEditText(
            modifier = Modifier.fillMaxWidth(),
            text = "Preview Text",
            onTextChange = {},
            prefix = Icons.Rounded.DateRange
        )
    }
}
