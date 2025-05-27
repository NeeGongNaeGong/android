package com.ssafy.neegongnaegong.data.mapper.learningrecord

import com.ssafy.neegongnaegong.data.mapper.TagMapper.toDomain
import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord

internal object LearningRecordMapper {

    fun LearningRecord.toCreateRequest() = CreateLearningRecordRequest(
        startAt = startAt,
        endAt = endAt,
        title = title,
        content = content,
        tags = tags.map { it.id },
    )

    fun LearningRecord.toUpdateRequest() = UpdateLearningRecordRequest(
        title = title,
        content = content,
        tags = tags.map { it.id },
    )

    fun GetLearningRecordResponse.toDomain() = LearningRecord(
        id = learningRecordId,
        title = title,
        content = content,
        startAt = startAt,
        endAt = endAt,
        tags = tags.toDomain(),
    )

    fun List<GetLearningRecordResponse>.toDomain() = map { it.toDomain() }
}
