package com.ssafy.neegongnaegong.data.model.learningrecord.request

data class UpdateLearningRecordRequest(
    val title: String,
    val content: String,
    val categoryId: Long,
    val tags: List<Long>
)
