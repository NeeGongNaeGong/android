package com.ssafy.neegongnaegong.domain.model.preview.personal

import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord

class PersonalPreviewDataProvider {
    fun getTags(): List<Tag> =
        listOf(
            Tag(id = 1, koName = "공부", enName = "study"),
            Tag(id = 2, koName = "운동", enName = "exercise"),
            Tag(id = 3, koName = "네트워크", enName = "network"),
            Tag(id = 4, koName = "CS", enName = "cs"),
        )

    fun getStudyRecord(): StudyRecord =
        StudyRecord(
            id = 0,
            title = "수학 공부",
            content = "미적분 복습",
            startTime = "2025-04-14T18:33:02.856Z",
            endTime = "2025-04-14T20:33:02.856Z",
            tags = listOf("CS", "네트워크", "Kotlin"),
        )

    fun getStudyRecords(): List<StudyRecord> =
        listOf(
            StudyRecord(
                id = 1,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("재테크", "미술"),
            ),
            StudyRecord(
                id = 2,
                title = "영어 단어 영어 단어 영어 단어",
                content = "VOCA 2200 암기",
                startTime = "2025-04-14T06:33:02.856Z",
                endTime = "2025-04-14T08:33:02.856Z",
                tags = listOf("회계"),
            ),
            StudyRecord(
                id = 3,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("CS", "미술"),
            ),
            StudyRecord(
                id = 4,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("백엔드", "네트워크"),
            ),
            StudyRecord(
                id = 5,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("백엔드"),
            ),
            StudyRecord(
                id = 6,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("CS", "네트워크"),
            ),
            StudyRecord(
                id = 7,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startTime = "2025-04-14T04:33:02.856Z",
                endTime = "2025-04-14T06:33:02.856Z",
                tags = listOf("CS", "네트워크"),
            ),
        )
}
