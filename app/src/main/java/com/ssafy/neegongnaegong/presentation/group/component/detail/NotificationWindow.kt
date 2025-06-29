package com.ssafy.neegongnaegong.presentation.group.component.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContent
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.getRelativeTimeString
import com.ssafy.neegongnaegong.presentation.util.toDateString
import java.time.LocalDateTime

/**
 * 공지 및 투표
 *
 * @param readStatus 읽음 상태 `true` 읽음, `false` 안읽음
 */
@Composable
fun LatestContentWindow(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    iconColor: Color,
    notification: StudiesLatestContent?,
    readStatus: Boolean,
    onClick: (Long) -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .clickable(onClick = { if (notification != null) onClick(notification.id) }),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 아이콘
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(icon),
                tint = iconColor,
                contentDescription = "",
            )
            if (notification == null) {
                Text(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(start = 10.dp),
                    text = "등록된 내용이 없습니다.",
                    color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
                return@Row
            }
            Row(
                modifier =
                    Modifier
                        .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier =
                        Modifier
                            .padding(start = 8.dp, end = 4.dp),
                    text = notification.title,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
                if (readStatus.not()) {
                    Text(
                        modifier =
                            Modifier
                                .background(
                                    color = NeeGongNaeGongTheme.colorScheme.peach,
                                    shape = RoundedCornerShape(4.dp),
                                )
                                .padding(horizontal = 2.dp),
                        text = "new",
                        color = NeeGongNaeGongTheme.colorScheme.background,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        style =
                            TextStyle(
                                platformStyle =
                                    PlatformTextStyle(
                                        includeFontPadding = false,
                                    ),
                            ),
                    )
                }
            }
            when (notification) {
                is StudiesLatestContent.LatestNotice -> {
                    val relativeTime = getRelativeTimeString(notification.createdAt)
                    // 상대적 시간
                    Text(
                        text = relativeTime,
                        color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        fontSize = 14.sp,
                    )
                }

                is StudiesLatestContent.LatestVote -> {
                    // 종료 시간
                    Text(
                        text = "종료 : ${notification.endTime.toDateString()}",
                        color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewStudiesNoticeWindow() {
    val sampleAnnouncement =
        StudiesLatestContent.LatestNotice(
            id = 1,
            title = "5월 정기모임",
            createdAt = LocalDateTime.now(),
        )
    NeeGongNaeGongTheme {
        LatestContentWindow(
            icon = R.drawable.ic_studies_detail_notice,
            iconColor = NeeGongNaeGongTheme.colorScheme.blue,
            notification = sampleAnnouncement,
            readStatus = true,
            onClick = { },
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewStudiesVotingWindow() {
    val sampleVoting =
        StudiesLatestContent.LatestVote(
            id = 2,
            title = "점심메뉴 추천",
            endTime = LocalDateTime.now().plusDays(1),
        )
    NeeGongNaeGongTheme {
        LatestContentWindow(
            icon = R.drawable.ic_studies_detail_voting,
            iconColor = NeeGongNaeGongTheme.colorScheme.lightGreen,
            notification = sampleVoting,
            readStatus = false,
            onClick = { },
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
private fun PreviewStudiesEmptyWindow() {
    NeeGongNaeGongTheme {
        LatestContentWindow(
            icon = R.drawable.ic_studies_detail_voting,
            iconColor = NeeGongNaeGongTheme.colorScheme.lightGreen,
            notification = null,
            readStatus = false,
            onClick = { },
        )
    }
}
