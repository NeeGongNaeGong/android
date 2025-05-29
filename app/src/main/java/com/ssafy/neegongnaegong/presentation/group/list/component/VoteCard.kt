package com.ssafy.neegongnaegong.presentation.group.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme

@Composable
fun VoteCard(
    title: String,
    participationMember: Int,
    onClick: () -> Unit,
) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            style = NeeGongNaeGongTheme.typography.bodyMedium,
        )
        Text(
            text = participationMember.toString(),
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            style = NeeGongNaeGongTheme.typography.bodySmall,
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewVoteCard() {
    VoteCard("테스트 투표입니다", 1) {}
}
