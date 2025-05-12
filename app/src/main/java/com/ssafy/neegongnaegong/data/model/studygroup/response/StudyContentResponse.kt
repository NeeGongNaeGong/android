package com.ssafy.neegongnaegong.data.model.studygroup.response

import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo
import java.time.LocalDateTime

data class StudyContentResponse(
    val learningRecordId: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val title: String,
    val content: String,
    val tags: List<TagResponse>,
    val learningRecordCreatedAt: LocalDateTime,
    val learningRecordModifiedAt: LocalDateTime
) {
    fun toStudyContentInfo() = StudyContentInfo(
        learningRecordId = learningRecordId,
        startAt = startAt,
        endAt = endAt,
        title = title,
        content = content,
        tags = tags.map { it.toTagInfo() },
        learningRecordCreatedAt = learningRecordCreatedAt,
        learningRecordModifiedAt = learningRecordModifiedAt
    )

    fun toStudyRecord() = StudyRecord(
        id = learningRecordId,
        title = title,
        content = content,
        startTime = startAt.toString().plus("z"),
        endTime = endAt.toString().plus("z"),
        tags = tags.map { it.name }

    )

}