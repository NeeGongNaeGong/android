package com.ssafy.neegongnaegong.data.model.learningrecord.response

data class CursorSlice(
    val content: List<GetLearningRecordResponse>,
    val hasNext: Boolean,
    val cursorCreatedAt: String?,
    val cursorId: Long?
)
