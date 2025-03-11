package com.ssafy.neegongnaegong.presentation.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.presentation.timer.component.GuideText
import com.ssafy.neegongnaegong.presentation.timer.component.MainCharacterImage
import com.ssafy.neegongnaegong.presentation.timer.component.PauseButton
import com.ssafy.neegongnaegong.presentation.timer.component.PauseDialog
import com.ssafy.neegongnaegong.presentation.timer.component.TimerText
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun TimerScreen(viewModel: TimerViewModel = hiltViewModel()) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TimerContract.Effect.ShowPauseDialog -> {

                }
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(screenHeight * 0.1f))

        TimerText()

        Spacer(modifier = Modifier.height(screenHeight * 0.05f))

        GuideText()

        Spacer(modifier = Modifier.height(screenHeight * 0.03f))

        MainCharacterImage()

        PauseButton(onClick = { viewModel.setEvent(TimerContract.Event.OnPauseClicked) })

        Spacer(modifier = Modifier.height(screenHeight * 0.03f))
    }

    if (uiState.isPauseDialogVisible) {
        PauseDialog(
            onDismiss = { viewModel.setEvent(TimerContract.Event.OnDismissDialog) },
            onConfirm = { viewModel.setEvent(TimerContract.Event.OnAcceptDialog) }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TimerPreview() {

    NeeGongNaeGongTheme {
        TimerScreen()
    }
}
