package com.ssafy.neegongnaegong.data.model.learningrecord.request

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord

data class UpdateLearningRecordRequest(
    val title: String,
    val content: String,
    val tags: List<Long>,
) {
    companion object {
        fun fromDomain(learningRecord: LearningRecord) =
            UpdateLearningRecordRequest(
                title = learningRecord.title,
                content = learningRecord.content,
                tags = learningRecord.tags.map { it.id },
            )
    }
}
