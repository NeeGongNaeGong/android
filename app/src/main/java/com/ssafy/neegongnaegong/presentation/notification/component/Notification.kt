package com.ssafy.neegongnaegong.presentation.notification.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlin.math.roundToInt

private val ICON_SIZE = 40.dp
private val MAX_SLIDE_DISTANCE = 55.dp

@Composable
fun Notification(
    modifier: Modifier = Modifier,
    image: String,
    user: String,
    content: String,
    isRead: Boolean,
    onDelete: () -> Unit,
    onMove: () -> Unit
) {
    val offsetX: MutableFloatState = remember { mutableFloatStateOf(0f) }
    val maxSlideDistancePx: Float = with(LocalDensity.current) { MAX_SLIDE_DISTANCE.toPx() }
    val animatedOffsetX: Float by animateFloatAsState(
        targetValue = offsetX.floatValue,
        animationSpec = tween(durationMillis = 200),
        label = "offsetX"
    )

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .size(MAX_SLIDE_DISTANCE)
                    .background(color = Color.Red)
                    .clickable(onClick = onDelete),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        NotificationContent(
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .clickable { onMove() }
                .draggableToRevealAction(
                    offsetState = offsetX,
                    maxSlideDistancePx = maxSlideDistancePx
                ),
            image = image,
            user = user,
            content = content,
            isRead = isRead
        )
    }
}

@Composable
private fun NotificationContent(
    modifier: Modifier = Modifier,
    image: String,
    user: String,
    content: String,
    isRead: Boolean,
) {
    val color: Color = notificationBackgroundColor(isRead = isRead)

    Row(
        modifier = modifier
            .background(color = color)
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .size(ICON_SIZE)
                .clip(CircleShape), // 원형 클리핑
            imageModel = { image },
            failure = {
                Image(
                    painter = painterResource(id = R.drawable.img_default_profile),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier
                        .size(ICON_SIZE)
                        .clip(CircleShape)
                )
            }
        )

        Text(
            modifier = Modifier.weight(1f),
            text = buildText(user = user, content = content),
            style = NeeGongNaeGongTheme.typography.bodySmall,
            color = NeeGongNaeGongTheme.colorScheme.primaryText
        )
    }
}

@Composable
private fun notificationBackgroundColor(isRead: Boolean): Color {
    val isDark: Boolean = isSystemInDarkTheme()
    return if (isDark) {
        if (isRead) Color(0xFF0D1015) else Color.Gray
    } else {
        if (isRead) Color.White else Color(0xFFEFF6FE)
    }
}

private fun buildText(user: String, content: String): AnnotatedString = buildAnnotatedString {
    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
        append(user)
    }
    append(content)
}

fun Modifier.draggableToRevealAction(
    offsetState: MutableState<Float>,
    maxSlideDistancePx: Float
): Modifier = pointerInput(Unit) {
    detectHorizontalDragGestures(
        onDragEnd = {
            val newOffset = if (offsetState.value < -maxSlideDistancePx / 2) {
                -maxSlideDistancePx
            } else {
                0f
            }
            offsetState.value = newOffset
        }
    ) { _, dragAmount ->
        val newOffset = (offsetState.value + dragAmount).coerceIn(-maxSlideDistancePx, 0f)
        offsetState.value = newOffset
    }
}

@Composable
@Preview
fun NotificationPreview() {
    Notification(
        modifier = Modifier.fillMaxWidth(),
        image = "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTRI8A6M23RTePWn8of5fgwRzSMMzRy_6mZP7OrP79VF3ByzCoRcyfx6bYr9w4bH9zdVfpV_LP9hBAudM5SRGyjnbbEhnrs2vWZKF8wySI",
        user = "킹민조",
        content = "님이 게시글을 삭제했습니다.",
        isRead = false,
        onDelete = {},
        onMove = {}
    )
}
