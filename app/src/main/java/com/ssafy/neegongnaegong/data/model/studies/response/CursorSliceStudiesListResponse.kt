package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage

data class CursorSliceStudiesListResponse(
    val content: List<StudiesResponse>,
    val hasNext: Boolean,
    val cursorCreatedAt: String,
    val cursorId: Long,
) {
    fun toDomain(): CursorStudiesPage =
        CursorStudiesPage(
            content = content.map { it.toDomain() },
            hasNext = hasNext,
            cursorCreatedAt = cursorCreatedAt,
            cursorId = cursorId,
        )
}
