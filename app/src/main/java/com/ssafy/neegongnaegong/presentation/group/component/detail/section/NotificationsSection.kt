package com.ssafy.neegongnaegong.presentation.group.component.detail.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.NotificationData
import com.ssafy.neegongnaegong.presentation.group.component.detail.NotificationWindow
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun NotificationsSection(
    modifier: Modifier = Modifier,
    announcements: NotificationData,
    voting: NotificationData,
    onAnnouncementClick: () -> Unit,
    onVotingClick: () -> Unit,
) {
    Surface(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            NotificationWindow(
                modifier = Modifier.weight(1f).padding(2.dp),
                icon = R.drawable.ic_studies_detail_announcement,
                notification = announcements,
                onNotificationClick = onAnnouncementClick,
            )
            NotificationWindow(
                modifier = Modifier.weight(1f).padding(2.dp),
                icon = R.drawable.ic_studies_detail_voting,
                notification = voting,
                onNotificationClick = onVotingClick,
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewNotificationsSection() {
    NeeGongNaeGongTheme {
        NotificationsSection(
            modifier = Modifier,
            announcements =
                NotificationData(
                    id = 1,
                    title = "5월 모임 공지",
                    dateTime = "2023.11.01 09:30:00",
                ),
            voting =
                NotificationData(
                    id = 1,
                    title = "점메추 투표",
                    dateTime = "2023.11.01 09:30:00",
                ),
            onAnnouncementClick = {},
            onVotingClick = {},
        )
    }
}
