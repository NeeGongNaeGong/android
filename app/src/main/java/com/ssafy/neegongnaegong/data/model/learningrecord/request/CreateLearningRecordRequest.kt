package com.ssafy.neegongnaegong.data.model.learningrecord.request

data class CreateLearningRecordRequest(
    val startAt: String,
    val endAt: String,
    val title: String,
    val content: String,
    val tags: List<Long>
)
