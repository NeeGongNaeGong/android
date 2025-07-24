package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

data class GetStudiesWeeklyRankingsResponse(
    val content: List<GetStudiesWeeklyRankingsMemberResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData,
)
