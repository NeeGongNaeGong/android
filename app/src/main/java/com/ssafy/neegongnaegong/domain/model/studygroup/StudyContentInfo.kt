package com.ssafy.neegongnaegong.domain.model.studygroup

import com.ssafy.neegongnaegong.data.mapper.tag.TagInfoMapper.toDomain
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
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
    val cursorId: Long,
)

fun StudyContentInfo.toLearningRecord() =
    LearningRecord(
        id = learningRecordId,
        title = title,
        content = content,
        startAt = startAt,
        endAt = endAt,
        tags = tags.toDomain(),
    )
