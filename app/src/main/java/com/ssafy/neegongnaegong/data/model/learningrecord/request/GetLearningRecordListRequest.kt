package com.ssafy.neegongnaegong.data.model.learningrecord.request

data class GetLearningRecordListRequest(
    val tag: List<Long>? = null,
    val targetDate: String? = null,
    val cursorValue: String? = null,
    val cursorId: Long? = null,
    val size: Int = 20,
)
