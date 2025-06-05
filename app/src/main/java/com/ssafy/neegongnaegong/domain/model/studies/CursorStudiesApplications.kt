package com.ssafy.neegongnaegong.domain.model.studies

data class CursorStudiesApplications(
    val content: List<StudiesApplicationsMember>,
    val hasNext: Boolean,
    val cursorId: Long?,
)
