package com.ssafy.neegongnaegong.domain.model.preview.personal

import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.write.Tag

class PersonalPreviewDataProvider {

    fun getTags(): List<Tag> {
        return listOf(
            Tag(koName = "공부", enName = "study"),
            Tag(koName = "운동", enName = "exercise"),
            Tag(koName = "네트워크", enName = "network"),
            Tag(koName = "CS", enName = "cs"),
        )
    }

    fun getStudyRecord(): StudyRecord = StudyRecord(
        title = "수학 공부",
        content = "미적분 복습",
        startTime = "2025-04-14T18:33:02.856Z",
        endTime = "2025-04-14T20:33:02.856Z",
        tags = listOf("CS", "네트워크", "Kotlin")
    )

    fun getStudyRecords(): List<StudyRecord> {
        return listOf(
            StudyRecord(
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("재테크", "미술")
            ),
            StudyRecord(
                title = "영어 단어 영어 단어 영어 단어",
                content = "VOCA 2200 암기",
                startTime = "2025-04-14T06:33:02.856Z",
                endTime = "2025-04-14T08:33:02.856Z",
                tags = listOf("회계")
            ),
            StudyRecord(
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("CS", "미술")
            ),
            StudyRecord(
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("백엔드", "네트워크")
            ),
            StudyRecord(
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("백엔드")
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
    }
}