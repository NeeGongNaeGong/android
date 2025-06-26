package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import java.time.LocalDateTime

data class GetStudiesFeedsResponse(
    val content: List<GetLearningRecordResponse>,
    val hasNext: Boolean,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long,
    val size: Int,
)
