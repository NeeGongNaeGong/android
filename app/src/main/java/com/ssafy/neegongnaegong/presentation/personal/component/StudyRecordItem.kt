package com.ssafy.neegongnaegong.presentation.personal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography
import com.ssafy.neegongnaegong.presentation.util.toHourMinuteString
import com.ssafy.neegongnaegong.presentation.util.toTimeString

@Composable
fun StudyRecordItem(
    record: StudyRecord,
    onClick: (Long) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .clickable(onClick = { onClick(record.id) })
            .padding(16.dp)
    ) {
        Column {
            // 제목 + 시간
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = record.title,
                    style = NeeGongNaeGongTheme.typography.titleMedium.copy(fontSize = 20.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.width(8.dp))

                val start = record.startTime.toTimeString()
                val end = record.endTime.toHourMinuteString()

                Text(
                    text = "$start ~ $end",
                    style =  NeeGongNaeGongTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            // 내용
            Text(
                text = record.content,
                style = NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = 12.sp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )


            Spacer(modifier = Modifier.height(12.dp))

            // 태그
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                record.tags.forEach { tag ->
                    Text(
                        text = "#$tag",
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyRecordItemPreview() {
    StudyRecordItem(record = PersonalPreviewDataProvider().getStudyRecord())
}

