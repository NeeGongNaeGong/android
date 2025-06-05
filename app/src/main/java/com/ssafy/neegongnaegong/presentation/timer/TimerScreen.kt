package com.ssafy.neegongnaegong.presentation.timer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.timer.component.timer.GuideText
import com.ssafy.neegongnaegong.presentation.timer.component.timer.LearningCancelDialog
import com.ssafy.neegongnaegong.presentation.timer.component.timer.MainCharacterImage
import com.ssafy.neegongnaegong.presentation.timer.component.timer.PauseButton
import com.ssafy.neegongnaegong.presentation.timer.component.timer.PauseDialog
import com.ssafy.neegongnaegong.presentation.timer.component.timer.PlayButton
import com.ssafy.neegongnaegong.presentation.timer.component.timer.TimerText
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun TimerRoute(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = hiltViewModel(),
    onCloseActivity: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler { viewModel.setEvent(TimerContract.Event.OnLearningCancelDialogShow) }

    TimerContent(
        modifier = modifier,
        uiState = uiState,
        effect = viewModel.effect,
        onPauseClicked = { viewModel.setEvent(TimerContract.Event.OnPauseClicked) },
        onPlayClicked = { viewModel.setEvent(TimerContract.Event.OnPlayClicked) },
        onCancelDialog = { viewModel.setEvent(TimerContract.Event.OnCancelDialog) },
        onDismissDialog = { viewModel.setEvent(TimerContract.Event.OnDismissDialog) },
        onConfirmDialog = { viewModel.setEvent(TimerContract.Event.OnConfirmDialog) },
        navigateToWriteScreen = { },
        onLearningCancelDialogCancel = { viewModel.setEvent(TimerContract.Event.OnLearningCancelDialogDismiss) },
        onLearningCancelDialogDismiss = { viewModel.setEvent(TimerContract.Event.OnLearningCancelDialogDismiss) },
        onLearningCancelDialogConfirm = { viewModel.setEvent(TimerContract.Event.OnLearningCancelDialogConfirm) },
        onCloseActivity = onCloseActivity,
    )
}

@Composable
fun TimerContent(
    modifier: Modifier = Modifier,
    effect: Flow<TimerContract.Effect>,
    uiState: TimerContract.State,
    // play button
    onPauseClicked: () -> Unit,
    onPlayClicked: () -> Unit,
    // Pause dialog
    onCancelDialog: () -> Unit,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit,
    navigateToWriteScreen: () -> Unit,
    // Cancel dialog
    onLearningCancelDialogCancel: () -> Unit,
    onLearningCancelDialogDismiss: () -> Unit,
    onLearningCancelDialogConfirm: () -> Unit,
    // activity
    onCloseActivity: () -> Unit,
) {
    val context = LocalContext.current

    if (uiState.isPauseDialogVisible) {
        PauseDialog(
            onCancel = onCancelDialog,
            onDismiss = onDismissDialog,
            onConfirm = onConfirmDialog,
        )
    }

    if (uiState.isLearningCancelDialogShow) {
        LearningCancelDialog(
            onCancel = onLearningCancelDialogCancel,
            onDismiss = onLearningCancelDialogDismiss,
            onConfirm = onLearningCancelDialogConfirm,
        )
    }

    LaunchedEffect(effect) {
        effect.collect { effect ->
            when (effect) {
                is TimerContract.Effect.NavigateToWriteScreen -> {
                    navigateToWriteScreen()
                }

                is TimerContract.Effect.CloseTimerActivity -> {
                    onCloseActivity()
                }
            }
        }
    }

    TimerScreen(
        modifier = modifier,
        totalElapsedTime = uiState.totalElapsedTime,
        isRunning = uiState.isRunning,
        onPauseClicked = onPauseClicked,
        onPlayClicked = onPlayClicked,
    )

    if (uiState.isLoading) LoadingDialog()
}

@Composable
fun TimerScreen(
    modifier: Modifier = Modifier,
    totalElapsedTime: Long,
    // play button
    isRunning: Boolean,
    onPauseClicked: () -> Unit,
    onPlayClicked: () -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(NeeGongNaeGongTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(screenHeight * 0.1f))

        TimerText(elapsedTime = totalElapsedTime)

        Spacer(modifier = Modifier.height(screenHeight * 0.05f))

        GuideText()

        Spacer(modifier = Modifier.height(screenHeight * 0.03f))

        MainCharacterImage()

        if (isRunning) {
            PauseButton(onClick = onPauseClicked)
        } else {
            PlayButton(onClick = onPlayClicked)
        }

        Spacer(modifier = Modifier.height(screenHeight * 0.03f))
    }
}

@NeeGongNaeGongPreviews
@Composable
fun TimerScreenPreview() {
    NeeGongNaeGongTheme {
        TimerScreen(
            totalElapsedTime = 90_000L,
            isRunning = false,
            onPauseClicked = {},
            onPlayClicked = {},
        )
    }
}
