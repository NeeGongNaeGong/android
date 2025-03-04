package com.ssafy.neegongnaegong.presentation.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.presentation.timer.component.GuideText
import com.ssafy.neegongnaegong.presentation.timer.component.MainCharacterImage
import com.ssafy.neegongnaegong.presentation.timer.component.PauseButton
import com.ssafy.neegongnaegong.presentation.timer.component.TimerText
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun TimerScreen() {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(screenHeight * 0.16f))

        TimerText()

        Spacer(modifier = Modifier.height(screenHeight * 0.05f))

        GuideText()

        Spacer(modifier = Modifier.height(screenHeight * 0.03f))

        MainCharacterImage()

        Spacer(modifier = Modifier.height(screenHeight * 0.05f))

        PauseButton(onClick = {/*todo */ })
    }
}


@Preview(showBackground = true)
@Composable
fun TimerPreview() {

    NeeGongNaeGongTheme {
        TimerScreen()
    }
}