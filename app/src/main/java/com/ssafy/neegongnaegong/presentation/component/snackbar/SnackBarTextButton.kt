package com.ssafy.neegongnaegong.presentation.component.snackbar

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
internal fun SnackBarTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
) {
    Text(
        modifier = modifier.clickable(enable) { onClick() },
        text = text,
        color = NeeGongNaeGongTheme.colorScheme.blue,
        style = NeeGongNaeGongTheme.typography.bodySmall
    )
}

@NeeGongNaeGongPreviews
@Composable
private fun SnackBarTextButtonPreview() {
    NeeGongNaeGongTheme {
        SnackBarTextButton("확인", {})
    }
}