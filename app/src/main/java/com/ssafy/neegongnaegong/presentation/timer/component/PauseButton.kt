package com.ssafy.neegongnaegong.presentation.timer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R

@Composable
fun PauseButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Image(
        modifier = modifier
            .size(92.dp)
            .clickable { onClick.invoke() },
        painter = painterResource(id = R.drawable.btn_pause),
        contentDescription = stringResource(R.string.txt_timer_pause),
    )
}