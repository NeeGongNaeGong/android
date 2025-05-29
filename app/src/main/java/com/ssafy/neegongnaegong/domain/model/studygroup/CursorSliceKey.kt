package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDateTime

data class CursorSliceKey(
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long,
)
