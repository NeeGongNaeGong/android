package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.mapper.TagMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyContentResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyContentInfo

internal object StudyContentInfoMapper {

    fun StudyContentResponse.toDomain() = StudyContentInfo(
        learningRecordId = learningRecordId,
        startAt = startAt,
        endAt = endAt,
        title = title,
        content = content,
        tags = tags.toDomain(),
        learningRecordCreatedAt = learningRecordCreatedAt,
        learningRecordModifiedAt = learningRecordModifiedAt,
        cursorCreatedAt = cursorCreatedAt,
        cursorId = cursorId
    )

    fun List<StudyContentResponse>.toDomain() = map { it.toDomain() }
}
