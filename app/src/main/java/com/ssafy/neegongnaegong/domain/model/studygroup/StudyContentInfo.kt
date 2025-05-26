package com.ssafy.neegongnaegong.domain.model.studygroup

import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import java.time.LocalDateTime

data class StudyContentInfo(
    val learningRecordId: Long,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val title: String,
    val content: String,
    val tags: List<TagInfo>,
    val learningRecordCreatedAt: LocalDateTime,
    val learningRecordModifiedAt: LocalDateTime,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long
)

fun StudyContentInfo.toStudyRecord() = StudyRecord(
    id = learningRecordId,
    title = title,
    content = content,
    startTime = startAt.toString().plus("z"),
    endTime = endAt.toString().plus("z"),
    tags = tags.map { it.name }

)