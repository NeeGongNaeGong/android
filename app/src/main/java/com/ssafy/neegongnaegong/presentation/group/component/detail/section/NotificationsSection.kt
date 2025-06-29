package com.ssafy.neegongnaegong.presentation.group.component.detail.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContent
import com.ssafy.neegongnaegong.presentation.group.component.detail.LatestContentWindow
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime

@Composable
fun NotificationsSection(
    modifier: Modifier = Modifier,
    notice: StudiesLatestContent.LatestNotice?,
    voting: StudiesLatestContent.LatestVote?,
    onNoticeClick: (Long) -> Unit,
    onVotingClick: (Long) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        LatestContentWindow(
            modifier = Modifier.fillMaxWidth(),
            icon = R.drawable.ic_studies_detail_notice,
            iconColor = NeeGongNaeGongTheme.colorScheme.blue,
            notification = notice,
            readStatus = true,
            onClick = onNoticeClick,
        )
        LatestContentWindow(
            modifier = Modifier.fillMaxWidth(),
            icon = R.drawable.ic_studies_detail_voting,
            iconColor = NeeGongNaeGongTheme.colorScheme.lightGreen,
            notification = voting,
            readStatus = true,
            onClick = onVotingClick,
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewNotificationsSection() {
    NeeGongNaeGongTheme {
        NotificationsSection(
            modifier = Modifier,
            notice =
                StudiesLatestContent.LatestNotice(
                    id = 1,
                    title = "5월 모임 공지",
                    createdAt = LocalDateTime.now(),
                ),
            voting =
                StudiesLatestContent.LatestVote(
                    id = 2,
                    title = "점메추 투표",
                    endTime = LocalDateTime.now().plusDays(3),
                ),
            onNoticeClick = {},
            onVotingClick = {},
        )
    }
}
