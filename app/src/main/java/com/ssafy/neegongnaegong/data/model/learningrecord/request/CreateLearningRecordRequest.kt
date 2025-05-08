package com.ssafy.neegongnaegong.data.model.learningrecord.request

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import java.time.LocalDateTime

data class CreateLearningRecordRequest(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val title: String,
    val content: String,
    val tags: List<Long>,
) {
    companion object {
        fun fromDomain(learningRecord: LearningRecord) =
            CreateLearningRecordRequest(
                startAt = learningRecord.startAt,
                endAt = learningRecord.endAt,
                title = learningRecord.title,
                content = learningRecord.content,
                tags = learningRecord.tags.map { it.id },
            )
    }
}
