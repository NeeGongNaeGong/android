package com.ssafy.neegongnaegong.presentation.component.studyrecord

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.toDateString

@Composable
fun StudyRecordList(
    modifier: Modifier = Modifier,
    learningRecords: List<LearningRecord>,
    onClick: (Long) -> Unit,
    //paging
    onLoadMore: () -> Unit,
    hasNext: Boolean,
) {
    if (learningRecords.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "공부 기록이 없습니다.",
                style = NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = 16.sp),
                color = NeeGongNaeGongTheme.colorScheme.gray4
            )
        }
    } else {
        val groupedRecords = learningRecords
            .sortedByDescending { it.startAt }
            .groupBy { it.startAt.toDateString() }

        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            groupedRecords.forEach { (date, recordsForDate) ->
                item {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = date,
                        style = NeeGongNaeGongTheme.typography.bodySmall.copy(fontSize = 18.sp),
                    )
                }

                itemsIndexed(recordsForDate) { index, record ->
                    StudyRecordItem(record = record, onClick = onClick)

                    if (index == recordsForDate.lastIndex && hasNext) {
                        LaunchedEffect(Unit) {
                            onLoadMore()
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}

@NeeGongNaeGongPreviews
@Composable
fun StudyRecordListPreview() {
    StudyRecordList(
        learningRecords = PersonalPreviewDataProvider().getStudyRecords(),
        onClick = {},
        onLoadMore = {},
        hasNext = false,
    )
}
