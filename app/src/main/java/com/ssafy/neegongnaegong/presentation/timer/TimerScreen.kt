package com.ssafy.neegongnaegong.presentation.timer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography

@Composable
fun TimerScreen() {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(screenHeight * 0.16f))

        Text(
            text = "00 : 00 : 00",
            style = Typography.bodyLarge,
            fontSize = 64.sp
        )

        Spacer(modifier = Modifier.height(screenHeight * 0.05f))

        Text(
            text = stringResource(R.string.txt_timer_guide),
            style = Typography.bodyLarge,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.height(screenHeight * 0.03f))

        Image(
            painter = painterResource(id = R.drawable.img_main_character),
            contentDescription = stringResource(R.string.img_timer_main_character),
            modifier = Modifier.size(340.dp)
        )

        Spacer(modifier = Modifier.height(screenHeight * 0.05f))

        PauseButton(onClick = {/*todo */ })
    }
}

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

@Preview(showBackground = true)
@Composable
fun TimerPreview() {

    NeeGongNaeGongTheme {
        TimerScreen()
    }
}