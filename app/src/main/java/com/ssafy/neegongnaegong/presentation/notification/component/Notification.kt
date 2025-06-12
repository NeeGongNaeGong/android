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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlin.math.roundToInt

private val ICON_SIZE = 40.dp
private val MAX_SLIDE_DISTANCE = 55.dp
private val GROUP_JOIN_SLIDE_DISTANCE = 110.dp

private val AcceptColor = Color(0xFF4CAF50)
private val RejectColor = Color(0xFFFF5252)

// TODO(추후 형선이형 파트인 그룹 내 수락/거절 컴포넌트로 결합하겠습니다.)
@Composable
fun Notification(
    modifier: Modifier = Modifier,
    image: String,
    user: String,
    content: String,
    isRead: Boolean,
    isGroupJoinRequest: Boolean,
    onDelete: () -> Unit,
    onMove: () -> Unit,
    onAccept: () -> Unit,
    onReject: () -> Unit,
) {
    val offsetX: MutableFloatState = remember { mutableFloatStateOf(0f) }
    val rightDistancePx: Float = with(LocalDensity.current) { MAX_SLIDE_DISTANCE.toPx() }
    val leftDistancePx: Float = with(LocalDensity.current) { GROUP_JOIN_SLIDE_DISTANCE.toPx() }
    val animatedOffsetX: Float by animateFloatAsState(
        targetValue = offsetX.floatValue,
        animationSpec = tween(durationMillis = 150),
        label = "offsetX",
    )

    Box(modifier = modifier) {
        // 오른쪽 삭제 버튼
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxHeight()
                        .size(MAX_SLIDE_DISTANCE)
                        .background(color = Color.Red)
                        .clickable(onClick = onDelete),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }
        }

        // 왼쪽 수락/거절 버튼 (그룹 가입 요청인 경우만)
        if (isGroupJoinRequest) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .matchParentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                // 수락 버튼
                Box(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .width(GROUP_JOIN_SLIDE_DISTANCE / 2)
                            .background(color = AcceptColor)
                            .clickable(onClick = onAccept),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(id = R.string.accept),
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                        )

                        Text(
                            text = stringResource(id = R.string.accept),
                            color = Color.White,
                            style = TextStyle(fontSize = 10.sp),
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                // 거절 버튼
                Box(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .width(GROUP_JOIN_SLIDE_DISTANCE / 2)
                            .background(color = RejectColor)
                            .clickable(onClick = onReject),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = stringResource(id = R.string.reject),
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                        )

                        Text(
                            text = stringResource(id = R.string.reject),
                            color = Color.White,
                            style = TextStyle(fontSize = 10.sp),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }

        NotificationContent(
            modifier =
                Modifier
                    .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = onMove,
                    ).draggableToRevealAction(
                        offsetState = offsetX,
                        rightDistancePx = rightDistancePx,
                        leftDistancePx = leftDistancePx,
                        isGroupJoinRequest = isGroupJoinRequest,
                    ),
            image = image,
            user = user,
            content = content,
            isRead = isRead,
            isGroupJoinRequest = isGroupJoinRequest,
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
    isGroupJoinRequest: Boolean,
) {
    val color: Color = notificationBackgroundColor(isRead = isRead)

    Row(
        modifier =
            modifier
                .background(color = color)
                .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        GlideImage(
            modifier =
                Modifier
                    .size(ICON_SIZE)
                    .clip(CircleShape),
            imageModel = { image },
            failure = {
                Image(
                    painter = painterResource(id = R.drawable.img_default_profile),
                    contentDescription = "Default Profile Image",
                    modifier =
                        Modifier
                            .size(ICON_SIZE)
                            .clip(CircleShape),
                )
            },
        )

        Text(
            modifier = Modifier.weight(1f),
            text = buildText(user = user, content = content),
            style = NeeGongNaeGongTheme.typography.bodySmall,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
        )

        if (isGroupJoinRequest) {
            Text(
                text = stringResource(id = R.string.help_group_join),
                style = TextStyle(fontSize = 11.sp),
                color = Color.Gray,
            )
        }
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

private fun buildText(
    user: String,
    content: String,
): AnnotatedString =
    buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(user)
        }
        append(content)
    }

private fun Modifier.draggableToRevealAction(
    offsetState: MutableState<Float>,
    rightDistancePx: Float,
    leftDistancePx: Float,
    isGroupJoinRequest: Boolean,
): Modifier =
    pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragEnd = {
                val newOffset =
                    if (isGroupJoinRequest) {
                        // 그룹 가입 요청인 경우 양방향 드래그 지원
                        when {
                            offsetState.value > leftDistancePx / 2 -> leftDistancePx
                            offsetState.value < -rightDistancePx / 2 -> -rightDistancePx
                            else -> 0f
                        }
                    } else {
                        // 일반 알림인 경우 오른쪽으로만 드래그 (삭제)
                        if (offsetState.value < -rightDistancePx / 2) {
                            -rightDistancePx
                        } else {
                            0f
                        }
                    }
                offsetState.value = newOffset
            },
        ) { _, dragAmount ->
            val newOffset =
                if (isGroupJoinRequest) {
                    // 그룹 가입 요청인 경우: 양방향 드래그 허용
                    (offsetState.value + dragAmount).coerceIn(-rightDistancePx, leftDistancePx)
                } else {
                    // 일반 알림인 경우: 왼쪽으로만 드래그 허용 (삭제)
                    (offsetState.value + dragAmount).coerceIn(-rightDistancePx, 0f)
                }
            offsetState.value = newOffset
        }
    }

@Composable
@Preview()
fun NotificationPreview() {
    Notification(
        modifier = Modifier.fillMaxWidth(),
        image =
            "https://encrypted-tbn1.gstatic.com/" +
                "images?q=tbn:ANd9GcTRI8A6M23RTePWn8of5fgwRzSMMzRy" +
                "_6mZP7OrP79VF3ByzCoRcyfx6bYr9w4bH9zdVfpV_LP9hBAudM5SRGyjnbbEhnrs2vWZKF8wySI",
        user = "킹민조",
        content = "님이 그룹 가입을 요청했습니다.",
        isRead = false,
        isGroupJoinRequest = true,
        onDelete = {},
        onMove = {},
        onAccept = {},
        onReject = {},
    )
}
