package com.ssafy.neegongnaegong.domain.model.studies

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import java.time.LocalDateTime

data class CursorStudiesFeeds(
    val content: List<LearningRecord>,
    val hasNext: Boolean,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long?,
)
