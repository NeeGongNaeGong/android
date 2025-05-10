package com.ssafy.neegongnaegong.data.model.learningrecord.response

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import java.time.LocalDateTime

data class GetLearningRecordResponse(
    val learningRecordId: Long,
    val title: String,
    val content: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val tags: List<TagResponse>,
) {
    fun toDomain(): LearningRecord =
        LearningRecord(
            id = learningRecordId,
            title = title,
            content = content,
            startAt = startAt,
            endAt = endAt,
            tags = tags.map { it.toDomain() },
        )
}
