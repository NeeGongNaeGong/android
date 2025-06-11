package com.ssafy.neegongnaegong.presentation.group.list.notice.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.ssafy.neegongnaegong.domain.model.studygroup.Writer
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun NoticeCard(
    modifier: Modifier = Modifier,
    id: Long,
    title: String,
    createdAt: LocalDateTime,
    writer: Writer,
    onClick: (Long) -> Unit,
) {
    Column(
        modifier =
            modifier.fillMaxWidth().clickable { onClick(id) },
    ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = NeeGongNaeGongTheme.colorScheme.primaryText,
            style = NeeGongNaeGongTheme.typography.bodyMedium,
        )
        val formatter =
            DateTimeFormatter.ofPattern(
                "yyyy년 MM월 dd일 a hh:mm",
                Locale.getDefault(),
            )
        Text(
            text =
                "${createdAt.format(formatter)}ㆍ${writer.name}",
            overflow = TextOverflow.Ellipsis,
            color = NeeGongNaeGongTheme.colorScheme.secondaryText,
            style = NeeGongNaeGongTheme.typography.bodySmall,
        )
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewNoticeCard() {
    NoticeCard(id = 0, title = "테스트 투표입니다", createdAt = LocalDateTime.now(), writer = Writer(0, "누구누구", "", "")) {}
}
