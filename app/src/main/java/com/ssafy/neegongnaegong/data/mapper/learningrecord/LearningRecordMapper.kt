package com.ssafy.neegongnaegong.data.mapper.learningrecord

import com.ssafy.neegongnaegong.data.mapper.tag.LearningRecordTagMapper.toDomain
import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.AuthorResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordDatesByMonthResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import java.time.LocalDate

internal object LearningRecordMapper {
    fun LearningRecord.toCreateRequest() =
        CreateLearningRecordRequest(
            startAt = startAt,
            endAt = endAt,
            title = title,
            content = content,
            tags = tags.map { it.id },
        )

    fun LearningRecord.toUpdateRequest() =
        UpdateLearningRecordRequest(
            title = title,
            content = content,
            tags = tags.map { it.id },
        )

    fun GetLearningRecordResponse.toDomain() =
        LearningRecord(
            id = learningRecordId,
            title = title,
            content = content,
            startAt = startAt,
            endAt = endAt,
            tags = tags.toDomain(),
            author = author.toDomain(),
        )

    fun List<GetLearningRecordResponse>.toDomain() = map { it.toDomain() }

    fun GetLearningRecordDatesByMonthResponse.toLocalDates(): List<LocalDate> =
        days.mapNotNull { dateList ->
            val (year, month, day) = dateList
            LocalDate.of(year, month, day)
        }

    fun AuthorResponse.toDomain() =
        User(
            id = id,
            nickname = username,
            profileImg = profileImg,
        )
}
