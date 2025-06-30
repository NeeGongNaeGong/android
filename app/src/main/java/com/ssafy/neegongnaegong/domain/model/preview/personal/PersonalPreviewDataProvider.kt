package com.ssafy.neegongnaegong.domain.model.preview.personal

import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PersonalPreviewDataProvider {
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    fun getTags(): List<Tag> =
        listOf(
            Tag(id = 1, koName = "공부", enName = "study"),
            Tag(id = 2, koName = "운동", enName = "exercise"),
            Tag(id = 3, koName = "네트워크", enName = "network"),
            Tag(id = 4, koName = "CS", enName = "cs"),
            Tag(id = 5, koName = "재테크", enName = "finance"),
            Tag(id = 6, koName = "미술", enName = "art"),
            Tag(id = 7, koName = "회계", enName = "accounting"),
            Tag(id = 8, koName = "백엔드", enName = "backend"),
            Tag(id = 9, koName = "Kotlin", enName = "kotlin"),
        )

    private fun tagByKo(vararg names: String): List<Tag> {
        val tagMap = getTags().associateBy { it.koName }
        return names.mapNotNull { tagMap[it] }
    }

    fun getStudyRecord(): LearningRecord =
        LearningRecord(
            id = 0,
            title = "수학 공부",
            content = "미적분 복습",
            startAt = LocalDateTime.parse("2025-04-14T18:33:02.856Z", formatter),
            endAt = LocalDateTime.parse("2025-04-14T20:33:02.856Z", formatter),
            tags = tagByKo("CS", "네트워크", "Kotlin"),
            author =
                User(
                    id = 0,
                    nickname = "공부하는 호랑이23",
                    profileImg = "",
                ),
        )

    fun getStudyRecords(): List<LearningRecord> =
        listOf(
            LearningRecord(
                id = 1,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵...",
                startAt = LocalDateTime.parse("2025-04-14T04:33:02.856Z", formatter),
                endAt = LocalDateTime.parse("2025-04-14T06:33:02.856Z", formatter),
                tags = tagByKo("재테크", "미술"),
                author = User.default(),
            ),
            LearningRecord(
                id = 2,
                title = "영어 단어 영어 단어 영어 단어",
                content = "VOCA 2200 암기",
                startAt = LocalDateTime.parse("2025-04-14T06:33:02.856Z", formatter),
                endAt = LocalDateTime.parse("2025-04-14T08:33:02.856Z", formatter),
                tags = tagByKo("회계"),
                author = User.default(),
            ),
            LearningRecord(
                id = 3,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startAt = LocalDateTime.parse("2025-04-14T04:33:02.856Z", formatter),
                endAt = LocalDateTime.parse("2025-04-14T06:33:02.856Z", formatter),
                tags = tagByKo("CS", "미술"),
                author = User.default(),
            ),
            LearningRecord(
                id = 4,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startAt = LocalDateTime.parse("2025-04-14T04:33:02.856Z", formatter),
                endAt = LocalDateTime.parse("2025-04-14T06:33:02.856Z", formatter),
                tags = tagByKo("백엔드", "네트워크"),
                author = User.default(),
            ),
            LearningRecord(
                id = 5,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startAt = LocalDateTime.parse("2025-04-14T04:33:02.856Z", formatter),
                endAt = LocalDateTime.parse("2025-04-14T06:33:02.856Z", formatter),
                tags = tagByKo("백엔드"),
                author = User.default(),
            ),
            LearningRecord(
                id = 6,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startAt = LocalDateTime.parse("2025-04-14T04:33:02.856Z", formatter),
                endAt = LocalDateTime.parse("2025-04-14T06:33:02.856Z", formatter),
                tags = tagByKo("CS", "네트워크"),
                author = User.default(),
            ),
            LearningRecord(
                id = 7,
                title = "청산별곡 정주행",
                content = "오늘 공부한 내용은 얄리얄리 얄라셩...",
                startAt = LocalDateTime.parse("2025-04-14T04:33:02.856Z", formatter),
                endAt = LocalDateTime.parse("2025-04-14T06:33:02.856Z", formatter),
                tags = tagByKo("CS", "네트워크"),
                author = User.default(),
            ),
        )
}
