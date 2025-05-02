package com.ssafy.neegongnaegong.presentation.component.snackbar

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun NeeGongNaeGongSnackbar(
    modifier: Modifier = Modifier,
    message: String,
    backgroundColor: Color,
    prefix: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            prefix()
            Text(
                modifier = Modifier
                    .clipToBounds()
                    .basicMarquee()
                    .weight(1f),
                text = message,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                style = NeeGongNaeGongTheme.typography.bodySmall
            )
            action()
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun NeeGongNaeGongSnackbarPreview() {
    NeeGongNaeGongTheme {
        NeeGongNaeGongSnackbar(
            message = "메시지",
            backgroundColor = NeeGongNaeGongTheme.colorScheme.gray1,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun NeeGongNaeGongSnackbarPreview_Prefix_Actions_Marquee() {
    NeeGongNaeGongTheme {
        NeeGongNaeGongSnackbar(
            message = "메시지".repeat(100),
            backgroundColor = NeeGongNaeGongTheme.colorScheme.gray1,
            prefix = {
                Text(
                    "ⓘ",
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    style = NeeGongNaeGongTheme.typography.titleSmall
                )
            },
            action = {
                Text(
                    "확인",
                    color = NeeGongNaeGongTheme.colorScheme.blue,
                    style = NeeGongNaeGongTheme.typography.bodySmall
                )
            }
        )
    }
}
