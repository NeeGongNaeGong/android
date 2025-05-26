package com.ssafy.neegongnaegong.data.model.studygroup.response

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
    val learningRecordModifiedAt: LocalDateTime,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long
)

fun StudyContentResponse.toStudyContentInfo() = StudyContentInfo(
    learningRecordId = learningRecordId,
    startAt = startAt,
    endAt = endAt,
    title = title,
    content = content,
    tags = tags.map { it.toTagInfo() },
    learningRecordCreatedAt = learningRecordCreatedAt,
    learningRecordModifiedAt = learningRecordModifiedAt,
    cursorCreatedAt = cursorCreatedAt,
    cursorId = cursorId
)