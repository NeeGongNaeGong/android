package com.ssafy.neegongnaegong.domain.studygroup

import java.time.LocalDateTime

data class StudyContentInfo(
    val learningRecordId: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val title: String,
    val content: String,
    val tags: List<TagInfo>,
    val learningRecordCreatedAt: LocalDateTime,
    val learningRecordModifiedAt: LocalDateTime
)
