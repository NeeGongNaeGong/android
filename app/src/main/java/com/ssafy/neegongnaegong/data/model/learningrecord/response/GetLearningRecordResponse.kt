package com.ssafy.neegongnaegong.data.model.learningrecord.response

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import java.time.LocalDateTime

data class GetLearningRecordResponse(
    val id: Long,
    val title: String,
    val content: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val tags: List<Tag>,
) {
    fun toDomain(): LearningRecord =
        LearningRecord(
            id = id,
            title = title,
            content = content,
            startAt = startAt,
            endAt = endAt,
            tags = tags,
        )
}
