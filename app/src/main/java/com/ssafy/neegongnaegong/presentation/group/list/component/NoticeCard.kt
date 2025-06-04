package com.ssafy.neegongnaegong.presentation.group.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
    id: Long,
    title: String,
    createdAt: LocalDateTime,
    writer: Writer,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier.clickable {
                id
                onClick()
            },
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
    NoticeCard(0, "테스트 투표입니다", LocalDateTime.now(), Writer(0, "누구누구", "", "")) {}
}
