package com.ssafy.neegongnaegong.domain.model.studies

import com.ssafy.neegongnaegong.domain.model.cursor.NextCursorDomain

data class CursorStudiesPage(
    val content: List<Studies>,
    val hasNext: Boolean,
    val nextCursor: NextCursorDomain,
)
