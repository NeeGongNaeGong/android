package com.ssafy.neegongnaegong.presentation.notification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

private val ICON_SIZE = 40.dp

@Composable
fun Notification(
    modifier: Modifier = Modifier,
    image: String,
    user: String,
    content: String,
    isRead: Boolean,
    onDelete: () -> Unit
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
        )

        Icon(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = null,
                onClick = onDelete
            ),
            imageVector = Icons.Default.Close,
            contentDescription = user
        )
    }
}

@Composable
private fun notificationBackgroundColor(isRead: Boolean): Color {
    val isDark = isSystemInDarkTheme()
    val baseColor = if (isDark) Color.Gray else Color.White
    return if (isRead) lerp(baseColor, Color.LightGray, 0.5f)
    else baseColor
}

private fun buildText(user: String, content: String): AnnotatedString = buildAnnotatedString {
    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
        append(user)
    }
    append(content)
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
        onDelete = {}
    )
}
