package com.ssafy.neegongnaegong.data.model.learningrecord.request

import java.time.LocalDateTime

data class CreateLearningRecordRequest(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val title: String,
    val content: String,
    val tags: List<Long>,
)
