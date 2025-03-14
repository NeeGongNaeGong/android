package com.ssafy.neegongnaegong.presentation.timer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R

@Composable
fun MainCharacterImage() {
    Image(
        modifier = Modifier.size(340.dp),
        painter = painterResource(id = R.drawable.img_main_character),
        contentDescription = stringResource(R.string.img_timer_main_character),
    )
}
