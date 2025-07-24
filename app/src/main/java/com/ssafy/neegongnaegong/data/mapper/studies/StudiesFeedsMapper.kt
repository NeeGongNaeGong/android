package com.ssafy.neegongnaegong.data.mapper.studies

import com.ssafy.neegongnaegong.data.mapper.cursor.CursorMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.learningrecord.LearningRecordMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesFeedsResponse
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesFeeds

internal object StudiesFeedsMapper {
    fun GetStudiesFeedsResponse.toDomain() =
        CursorStudiesFeeds(
            content = content.map { it.toDomain() },
            hasNext = hasNext,
            nextCursor = nextCursor.toDomain(),
        )
}
