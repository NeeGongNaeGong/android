package com.ssafy.neegongnaegong.presentation.component.snackbar

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.neegongnaegong.presentation.base.SnackbarViewModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import kotlinx.coroutines.launch
import kotlin.math.hypot

@Composable
fun NeeGongNaeGongSnackbarHost() {
    val snackbarViewModel: SnackbarViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        snackbarViewModel.message.collect {
            val (message, type) = it
            val visuals = NeeGongNaeGongSnackbarVisuals(message, type)
            snackbarHostState.showSnackbar(visuals)
        }
    }

    SnackbarHost(hostState = snackbarHostState) {
        val visuals = it.visuals as? NeeGongNaeGongSnackbarVisuals ?: return@SnackbarHost
        val (message, type) = visuals
        val backgroundColor = when (type) {
            SnackbarManager.Type.Success -> NeeGongNaeGongTheme.colorScheme.gray3
            SnackbarManager.Type.Warning -> NeeGongNaeGongTheme.colorScheme.lightGreen
            SnackbarManager.Type.Error -> NeeGongNaeGongTheme.colorScheme.peach
            SnackbarManager.Type.None -> NeeGongNaeGongTheme.colorScheme.mint
        }

        Card(
            modifier = Modifier
                .dismissWithDragGestures { snackbarHostState.currentSnackbarData?.dismiss() }
                .padding(16.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = message,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
            )
        }
    }
}

@Composable
fun Modifier.dismissWithDragGestures(onDismiss: suspend () -> Unit): Modifier {
    val scope = rememberCoroutineScope()

    var offsetY by remember { mutableFloatStateOf(0f) }
    var offsetX by remember { mutableFloatStateOf(0f) }

    return this
        .offset { IntOffset(x = offsetX.toInt(), y = offsetY.toInt()) }
        .pointerInput(Unit) {
            detectDragGestures(
                onDrag = { change, dragAmount ->
                    change.consume()

                    offsetX += dragAmount.x
                    offsetY += dragAmount.y

                    val dragDistance = hypot(offsetX, offsetY)

                    // 어느 방향으로든 충분히 끌면 dismiss
                    if (dragDistance > 50f) {
                        scope.launch {
                            onDismiss()
                        }
                    }
                },
                onDragEnd = {
                    // 드래그 끝났는데 dismiss 안 했으면 원위치
                    offsetX = 0f
                    offsetY = 0f
                }
            )
        }
}
