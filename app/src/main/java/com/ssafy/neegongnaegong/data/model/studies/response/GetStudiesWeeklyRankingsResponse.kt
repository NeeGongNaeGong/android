package com.ssafy.neegongnaegong.data.model.studies.response

import java.time.LocalDateTime

data class GetStudiesWeeklyRankingsResponse(
    val content: List<GetStudiesWeeklyRankingsMemberResponse>,
    val hasNext: Boolean,
    val cursorTimeSeconds: Long,
    val cursorUserId: Long,
    val baseTime: LocalDateTime,
)
