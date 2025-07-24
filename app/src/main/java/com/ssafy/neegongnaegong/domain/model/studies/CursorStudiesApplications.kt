package com.ssafy.neegongnaegong.domain.model.studies

import com.ssafy.neegongnaegong.domain.model.cursor.NextCursorDomain

data class CursorStudiesApplications(
    val content: List<StudiesApplicationsMember>,
    val hasNext: Boolean,
    val nextCursor: NextCursorDomain,
)
