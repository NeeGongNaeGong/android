package com.ssafy.neegongnaegong.domain.model.studies

import com.ssafy.neegongnaegong.domain.model.cursor.NextCursorDomain

data class CursorStudiesWeeklyRankings(
    val content: List<WeeklyRankingsMember>,
    val hasNext: Boolean,
    val nextCursor: NextCursorDomain,
)
