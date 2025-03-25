package com.ssafy.neegongnaegong.presentation.timer.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R

@Composable
fun PlayButton(modifier: Modifier = Modifier, onClick: () -> Unit) {

    IconButton(
        modifier = modifier.size(100.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = stringResource(R.string.txt_timer_play),
            tint = Color.Black,
            modifier = Modifier.size(80.dp)
        )
    }
}
