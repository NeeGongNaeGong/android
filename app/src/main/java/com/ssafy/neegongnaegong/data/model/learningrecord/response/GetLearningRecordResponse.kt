package com.ssafy.neegongnaegong.data.model.learningrecord.response

import java.time.LocalDateTime

data class GetLearningRecordResponse(
    val learningRecordId: Long,
    val title: String,
    val content: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val tags: List<TagResponse>,
    val author: AuthorResponse,
    val learningRecordCreatedAt: LocalDateTime,
    val learningRecordModifiedAt: LocalDateTime,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long,
)
