package com.ssafy.neegongnaegong.presentation.component.snackbar

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.hypot

@Composable
internal fun Modifier.dismissWithDragGestures(onDismiss: suspend () -> Unit): Modifier {
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
