package com.ssafy.neegongnaegong.presentation.timer.component.timer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography
import com.ssafy.neegongnaegong.presentation.util.formatElapsedTime

@Composable
fun TimerText(elapsedTime: Long) {
    val formattedTime = remember(elapsedTime) { formatElapsedTime(elapsedTime) }
    Text(
        text = formattedTime,
        style = NeeGongNaeGongTheme.typography.bodyLarge.copy(
            fontFeatureSettings = "tnum",
        ),
        fontSize = 64.sp
    )
}
