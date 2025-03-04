package com.ssafy.neegongnaegong.presentation.timer.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun GuideText(){
    Text(
        text = stringResource(R.string.txt_timer_guide),
        style = Typography.bodyLarge,
        fontSize = 16.sp,
    )
}