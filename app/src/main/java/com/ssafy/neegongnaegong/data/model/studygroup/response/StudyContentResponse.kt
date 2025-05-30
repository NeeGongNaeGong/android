package com.ssafy.neegongnaegong.data.model.studygroup.response

import java.time.LocalDateTime

data class StudyContentResponse(
    val learningRecordId: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val title: String,
    val content: String,
    val tags: List<TagResponse>,
    val learningRecordCreatedAt: LocalDateTime,
    val learningRecordModifiedAt: LocalDateTime,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long,
)
