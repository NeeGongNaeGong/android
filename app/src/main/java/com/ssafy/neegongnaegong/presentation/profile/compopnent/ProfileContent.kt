package com.ssafy.neegongnaegong.presentation.profile.compopnent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    title: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.clickable(
            interactionSource = null,
            indication = null,
            onClick = onClick
        )
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = NeeGongNaeGongTheme.typography.bodyMedium,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(18.dp))

            icon()
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
    }
}
