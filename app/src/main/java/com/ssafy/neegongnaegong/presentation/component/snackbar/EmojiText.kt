package com.ssafy.neegongnaegong.presentation.component.snackbar

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
internal fun EmojiText(emoji: String) {
    Text(
        emoji,
        color = NeeGongNaeGongTheme.colorScheme.primaryText,
        style = NeeGongNaeGongTheme.typography.titleSmall,
    )
}
