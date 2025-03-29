package com.ssafy.neegongnaegong.presentation.timer

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun WriteScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: WriteViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {

    BackHandler { popBackStack() }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    WriteContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState.value,
        onCancelClicked = { viewModel.setEvent(WriteContract.Event.OnCancelClicked) },
        onConfirmClicked = { viewModel.setEvent(WriteContract.Event.OnConfirmClicked) },
        onTitleChanged = { viewModel.setEvent(WriteContract.Event.OnTitleChanged(it)) },
        onContentChanged = { viewModel.setEvent(WriteContract.Event.OnContentChanged(it)) },
        onTagPlusClicked = { viewModel.setEvent(WriteContract.Event.OnTagPlusClicked) },
        onTagEraseClicked = { viewModel.setEvent(WriteContract.Event.OnTagEraseClicked) }
    )

}


@Composable
fun WriteContent(
    modifier: Modifier = Modifier,
    effect: Flow<WriteContract.Effect>,
    uiState: WriteContract.State,
    onCancelClicked: () -> Unit,
    onConfirmClicked: () -> Unit,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onTagPlusClicked: () -> Unit,
    onTagEraseClicked: (String) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                is WriteContract.Effect.NavigateToHome -> {

                }

                is WriteContract.Effect.ShowErrorToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is WriteContract.Effect.ShowSuccessToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    WriteScreen(
        modifier = modifier,
        title = uiState.title,
        content = uiState.content,
        startTime = uiState.startTime,
        endTime = uiState.endTime
    )


}

@Composable
fun WriteScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    content: String = "",
    startTime: String = "",
    endTime: String = "",
) {
    
}