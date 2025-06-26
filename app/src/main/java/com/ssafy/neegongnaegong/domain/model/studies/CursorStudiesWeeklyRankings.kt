package com.ssafy.neegongnaegong.domain.model.studies

import java.time.LocalDateTime

data class CursorStudiesWeeklyRankings(
    val content: List<WeeklyRankingsMember>,
    val hasNext: Boolean,
    val cursorTimeSeconds: Long,
    val cursorUserId: Long,
    val baseTime: LocalDateTime,
)
