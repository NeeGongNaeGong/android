package com.ssafy.neegongnaegong.data.mapper.studies

import com.ssafy.neegongnaegong.data.mapper.cursor.CursorMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesWeeklyRankingsMemberResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesWeeklyRankingsResponse
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesWeeklyRankings
import com.ssafy.neegongnaegong.domain.model.studies.WeeklyRankingsMember
import com.ssafy.neegongnaegong.presentation.util.TimeUnit

internal object StudiesWeeklyRankingsMapper {
    fun GetStudiesWeeklyRankingsMemberResponse.toDomain() =
        WeeklyRankingsMember(
            userId = userId,
            name = name,
            profileImageUrl = profileImageUrl,
            studyTimeSeconds = studyTimeSeconds * TimeUnit.MINUTE.seconds, // TODO : (가중치 적용)
        )

    fun List<GetStudiesWeeklyRankingsMemberResponse>.toDomain() = map { it.toDomain() }

    fun GetStudiesWeeklyRankingsResponse.toDomain() =
        CursorStudiesWeeklyRankings(
            content = content.toDomain(),
            hasNext = hasNext,
            nextCursor = nextCursor.toDomain(),
        )
}
