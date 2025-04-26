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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun OptionButton(
    isSelected: Boolean,
    optionTitle: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        Row(
            modifier = modifier
                .padding(0.dp)
                .clickable {
                    onClick()
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,

                )
            Text(
                style = NeeGongNaeGongTheme.typography.labelMedium,
                text = optionTitle
            )
        }
    }
}

@Preview
@Composable
fun PreviewOptionButton() {
    OptionButton(false, "종료 시간") {}
}