package com.ssafy.neegongnaegong.presentation.group.component.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.NotificationData
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.getRelativeTimeString

/**
 * 스터디 알림 창
 */
@Composable
fun NotificationWindow(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    notification: NotificationData,
    onNotificationClick: () -> Unit = {},
) {
    val relativeTime = getRelativeTimeString(notification.dateTime)

    Surface(
        modifier =
            modifier
                .clickable(onClick = onNotificationClick),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 2.dp,
        color = NeeGongNaeGongTheme.colorScheme.gray1,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 아이콘
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(icon),
                tint = Color.Unspecified,
                contentDescription = "",
            )

            // 제목
            Text(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                text = notification.title,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )

            // 상대적 시간
            Text(
                text = relativeTime,
                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
@Preview
fun PreviewStudiesAnnouncementWindow() {
    val sampleAnnouncement =
        NotificationData(
            id = 1,
            title = "5월 정기모임",
            dateTime = "2023.11.01 09:30:00",
        )
    NeeGongNaeGongTheme {
        NotificationWindow(
            icon = R.drawable.ic_studies_detail_announcement,
            notification = sampleAnnouncement,
            onNotificationClick = { },
        )
    }
}

@Composable
@Preview
fun PreviewStudiesVotingWindow() {
    val sampleVoting =
        NotificationData(
            id = 2,
            title = "점심메뉴 추천",
            dateTime = "2023.11.01 09:30:00",
        )
    NeeGongNaeGongTheme {
        NotificationWindow(
            icon = R.drawable.ic_studies_detail_voting,
            notification = sampleVoting,
            onNotificationClick = { },
        )
    }
}
