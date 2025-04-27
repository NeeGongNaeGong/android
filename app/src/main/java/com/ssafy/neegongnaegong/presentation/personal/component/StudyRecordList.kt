package com.ssafy.neegongnaegong.presentation.personal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.preview.personal.PersonalPreviewDataProvider
import com.ssafy.neegongnaegong.presentation.ui.theme.Typography
import com.ssafy.neegongnaegong.presentation.util.toDateString

@Composable
fun StudyRecordList(
    modifier: Modifier = Modifier,
    studyRecords: List<StudyRecord>,
    onClick: (Long) -> Unit,
) {

    val groupedRecords = studyRecords
        .sortedByDescending { it.startTime }
        .groupBy { it.startTime.toDateString() }


    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        groupedRecords.forEach { (date, recordsForDate) ->
            item {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = date,
                    style = Typography.bodySmall.copy(
                        fontSize = 18.sp
                    ),
                )
            }

            items(recordsForDate) { record ->
                StudyRecordItem(
                    record = record,
                    onClick = onClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyRecordListPreview() {
    StudyRecordList(
        studyRecords = PersonalPreviewDataProvider().getStudyRecords(),
        onClick = {}
    )
}
