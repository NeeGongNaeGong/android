package com.ssafy.neegongnaegong.presentation.timer.component.write

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun DateTimeHeader(
    modifier: Modifier = Modifier,
    dateText: String,
    timeText: String,
) {
    Column(
        modifier =
            modifier
                .padding(start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.Center,
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
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            fontSize = 14.sp,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun DateTimeHeaderPreview() {
    NeeGongNaeGongTheme {
        DateTimeHeader(
            dateText = "2023년 10월 10일",
            timeText = "오후 3:45",
        )
    }
}
