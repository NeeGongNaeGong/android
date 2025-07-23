package com.ssafy.neegongnaegong.domain.model.cursor

data class NextCursorDomain(
    val cursorValue: String,
    val cursorId: Long,
    val first: Boolean,
)
