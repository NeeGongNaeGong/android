package com.ssafy.neegongnaegong.presentation.calendar.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun ScheduleEditText(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    placeHolder: String? = null,
    prefix: ImageVector? = null,
    enabled: Boolean = true,
) {
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
        enabled = enabled,
        value = text,
        onValueChange = onTextChange,
        textStyle = NeeGongNaeGongTheme.typography.bodyMedium,
        shape = RectangleShape,
        placeholder = {
            placeHolder?.let {
                Text(
                    text = placeHolder,
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
        },
        colors = MaterialTheme.textFieldColors()
    )
}

@Preview
@Composable
private fun ScheduleEditTextPreview() {
    NeeGongNaeGongTheme(dynamicColor = false) {
        ScheduleEditText(
            modifier = Modifier.fillMaxWidth(),
            text = "Preview Text",
            onTextChange = {},
            prefix = Icons.Rounded.DateRange
        )
    }
}
