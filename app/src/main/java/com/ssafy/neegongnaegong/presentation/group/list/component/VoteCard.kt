package com.ssafy.neegongnaegong.presentation.group.list.component

import android.icu.text.DecimalFormatSymbols
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun VoteCard(
    id: Long,
    title: String,
    participationMember: Int,
    voted: Boolean,
    endTime: LocalDateTime?,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier.clickable {
                id
            },
        horizontalArrangement = Arrangement.spacedBy(NeeGongNaeGongTheme.paddingScheme.sp2),
    ) {
        Text(
            text = "Q",
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            style = NeeGongNaeGongTheme.typography.titleLarge,
        )
        Column(modifier = Modifier.clickable { onClick() }) {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = NeeGongNaeGongTheme.colorScheme.primaryText,
                style = NeeGongNaeGongTheme.typography.bodyMedium,
            )
            Text(
                text = "${participationMember}명 참여ㆍ${if (voted) "참여" else "미참여"}",
                overflow = TextOverflow.Ellipsis,
                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                style = NeeGongNaeGongTheme.typography.bodySmall,
            )
            Text(
                text =
                    if (endTime != null) {
                        val formatter =
                            DateTimeFormatter.ofPattern(
                                "yyyy년 MM월 dd일 a hh:mm",
                                java.util.Locale.getDefault(),
                            )
                        "${
                            endTime.format(formatter)
                        } 종료"
                    } else {
                        DecimalFormatSymbols.getInstance().infinity
                    },
                color = NeeGongNaeGongTheme.colorScheme.secondaryText,
                overflow = TextOverflow.Ellipsis,
                style = NeeGongNaeGongTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewVoteCard() {
    VoteCard(0, "테스트 투표입니다", 1, false, LocalDateTime.now()) {}
}
