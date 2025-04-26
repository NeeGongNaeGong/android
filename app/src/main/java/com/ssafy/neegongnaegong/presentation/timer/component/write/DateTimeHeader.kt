package com.ssafy.neegongnaegong.presentation.timer.component.write

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun DateTimeHeader(
    dateText: String,
    timeText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 12.dp, end = 12.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = dateText,
            style = NeeGongNaeGongTheme.typography.titleMedium,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            fontSize = 20.sp,
        )
        Text(
            text = timeText,
            style = NeeGongNaeGongTheme.typography.bodySmall,
            fontSize = 14.sp,
        )
    }
}
