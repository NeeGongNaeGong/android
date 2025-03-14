package com.ssafy.neegongnaegong.presentation.timer.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun TimerText(){
    Text(
        text = "00 : 00 : 00",
        style = Typography.bodyLarge,
        fontSize = 64.sp
    )
}
