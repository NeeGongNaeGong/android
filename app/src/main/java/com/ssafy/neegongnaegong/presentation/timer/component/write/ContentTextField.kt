package com.ssafy.neegongnaegong.presentation.timer.component.write

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun ContentTextField(
    modifier: Modifier = Modifier,
    content: String,
    onContentChanged: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = content,
        onValueChange = onContentChanged,
        textStyle =
            NeeGongNaeGongTheme.typography.labelLarge.copy(
                fontSize = 18.sp,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                fontFeatureSettings = "tnum",
            ),
        placeholder = {
            Text(
                text = "내용을 입력하세요",
                fontSize = 18.sp,
                color = NeeGongNaeGongTheme.colorScheme.gray4,
                letterSpacing = 0.2.sp,
            )
        },
        maxLines = 5,
        colors =
            TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = NeeGongNaeGongTheme.colorScheme.gray4,
                unfocusedIndicatorColor = NeeGongNaeGongTheme.colorScheme.gray4,
                disabledIndicatorColor = NeeGongNaeGongTheme.colorScheme.gray3,
            ),
    )
}

@NeeGongNaeGongPreviews
@Composable
fun ContentTextFieldPreview() {
    NeeGongNaeGongTheme {
        ContentTextField(
            content = "",
            onContentChanged = {},
        )
    }
}
