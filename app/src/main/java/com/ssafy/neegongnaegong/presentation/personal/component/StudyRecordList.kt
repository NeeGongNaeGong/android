package com.ssafy.neegongnaegong.presentation.personal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord

@Composable
fun StudyRecordList(
    modifier: Modifier = Modifier,
    studyRecords: List<StudyRecord>,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(studyRecords) { record ->
            StudyRecordItem(record)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyRecordListPreview() {
    val dummyRecords = listOf(
        StudyRecord(
            title = "청산별곡 정주행",
            content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
            startTime = "2025-04-14T04:33:02.856Z",
            endTime = "2025-04-14T06:33:02.856Z",
            tags = listOf("CS", "네트워크")
        ),
        StudyRecord(
            title = "영어 단어 영어 단어 영어 단어",
            content = "VOCA 2200 암기",
            startTime = "2025-04-14T06:33:02.856Z",
            endTime = "2025-04-14T08:33:02.856Z",
            tags = listOf("CS", "운동")
        ),
        StudyRecord(
            title = "청산별곡 정주행",
            content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
            startTime = "2025-04-14T04:33:02.856Z",
            endTime = "2025-04-14T06:33:02.856Z",
            tags = listOf("CS", "네트워크")
        ),
        StudyRecord(
            title = "청산별곡 정주행",
            content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
            startTime = "2025-04-14T04:33:02.856Z",
            endTime = "2025-04-14T06:33:02.856Z",
            tags = listOf("CS", "네트워크")
        ),
        StudyRecord(
            title = "청산별곡 정주행",
            content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
            startTime = "2025-04-14T04:33:02.856Z",
            endTime = "2025-04-14T06:33:02.856Z",
            tags = listOf("CS", "네트워크")
        ),
        StudyRecord(
            title = "청산별곡 정주행",
            content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
            startTime = "2025-04-14T04:33:02.856Z",
            endTime = "2025-04-14T06:33:02.856Z",
            tags = listOf("CS", "네트워크")
        ),
        StudyRecord(
            title = "청산별곡 정주행",
            content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
            startTime = "2025-04-14T04:33:02.856Z",
            endTime = "2025-04-14T06:33:02.856Z",
            tags = listOf("CS", "네트워크")
        ),
    )

    StudyRecordList(studyRecords = dummyRecords)
}
