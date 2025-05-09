package com.ssafy.neegongnaegong.data.model.studies.response

import java.time.LocalDateTime

data class StudiesListResponse(
    val content: List<StudiesResponse>,
    val hasNext: Boolean,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Int,
)
