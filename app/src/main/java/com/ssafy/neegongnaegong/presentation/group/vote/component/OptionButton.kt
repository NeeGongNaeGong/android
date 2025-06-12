package com.ssafy.neegongnaegong.presentation.group.vote.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    isSelected: Boolean,
    optionTitle: String,
    onClick: () -> Unit,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        Row(
            modifier =
                modifier
                    .padding(0.dp)
                    .clickable {
                        if (enable) {
                            onClick()
                        }
                    },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                enabled = enable,
                selected = isSelected,
                onClick = onClick,
            )
            Text(
                style = NeeGongNaeGongTheme.typography.labelMedium,
                text = optionTitle,
                color = if (enable) NeeGongNaeGongTheme.colorScheme.primaryText else NeeGongNaeGongTheme.colorScheme.gray4,
            )
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun PreviewOptionButton() {
    NeeGongNaeGongTheme {
        OptionButton(isSelected = false, optionTitle = "종료 시간") {}
    }
}
