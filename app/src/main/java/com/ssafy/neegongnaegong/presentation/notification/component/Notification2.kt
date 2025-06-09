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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlin.math.roundToInt

private val ICON_SIZE = 40.dp
private val MAX_SLIDE_DISTANCE = 55.dp

@Composable
fun Notification2(
    modifier: Modifier = Modifier,
    image: String,
    user: String,
    content: String,
    isRead: Boolean,
    isGroupJoinRequest: Boolean,
    onDelete: () -> Unit,
    onMove: () -> Unit,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    val offsetX: MutableFloatState = remember { mutableFloatStateOf(0f) }
    val rightDistancePx: Float = with(LocalDensity.current) { MAX_SLIDE_DISTANCE.toPx() }
    val animatedOffsetX: Float by animateFloatAsState(
        targetValue = offsetX.floatValue,
        animationSpec = tween(durationMillis = 150),
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
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onMove
                )
                .draggableToRevealAction(
                    offsetState = offsetX,
                    rightDistancePx = rightDistancePx,
                ),
            image = image,
            user = user,
            content = content,
            isRead = isRead,
            isGroupJoinRequest = isGroupJoinRequest,
            onAccept = onAccept,
            onReject = onReject
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
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    val color: Color = notificationBackgroundColor(isRead = isRead)
    var showRejectDialog by remember { mutableStateOf(false) }

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
                .clip(CircleShape),
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

        if (isGroupJoinRequest) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // 거절 버튼 (Delete 스타일)
                Button(
                    onClick = { showRejectDialog = true },
                    modifier = Modifier.height(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        text = "거절",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // 수락 버튼 (Confirm 스타일)
                Button(
                    onClick = onAccept,
                    modifier = Modifier.height(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1DA1F2),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(
                        text = "수락",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    // 거절 확인 다이얼로그
    if (showRejectDialog) {
        AlertDialog(
            onDismissRequest = { showRejectDialog = false },
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            title = {
                Column {
                    Text(
                        text = "그룹 참여 요청 삭제",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            },
            text = {
                Column {
                    Text(
                        text = "${user}님의 그룹 참여 요청을 삭제하시겠습니까?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "삭제된 요청은 되돌릴 수 없습니다.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showRejectDialog = false
                        onReject()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "삭제하기",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showRejectDialog = false },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "취소",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            },
            shape = RoundedCornerShape(20.dp)
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

private fun Modifier.draggableToRevealAction(
    offsetState: MutableState<Float>,
    rightDistancePx: Float,
): Modifier = pointerInput(Unit) {
    detectHorizontalDragGestures(
        onDragEnd = {
            val newOffset = if (offsetState.value < -rightDistancePx / 2) {
                -rightDistancePx
            } else {
                0f
            }
            offsetState.value = newOffset
        }
    ) { _, dragAmount ->
        val newOffset = (offsetState.value + dragAmount).coerceIn(-rightDistancePx, 0f)
        offsetState.value = newOffset
    }
}