package com.ssafy.neegongnaegong.domain.model.studies

data class CursorStudiesPage(
    val content: List<Studies>,
    val hasNext: Boolean,
    val cursorCreatedAt: String,
    val cursorId: Long,
)