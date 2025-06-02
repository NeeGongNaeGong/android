package com.ssafy.neegongnaegong.presentation.component.button

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.material3.FloatingActionButtonElevation as FloatingActionButtonElevation1

@Composable
fun GradientFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = CircleShape,
    containerColor: Color = Color.Transparent,
    brushColor: Brush,
    elevation: FloatingActionButtonElevation1,
    content: @Composable () -> Unit,
) {
    // 회전 적용
    val infiniteTran = rememberInfiniteTransition()
    val angle by infiniteTran.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
    )
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        containerColor = containerColor,
        elevation = elevation,
    ) {
        Box(
            modifier = modifier,
        ) {
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .rotate(angle)
                        .background(
                            brush = brushColor,
                            shape = shape,
                        ),
            )
            content()
        }
    }
}
