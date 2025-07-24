package com.ssafy.neegongnaegong.domain.model.studies

import com.ssafy.neegongnaegong.domain.model.cursor.NextCursorDomain
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord

data class CursorStudiesFeeds(
    val content: List<LearningRecord>,
    val hasNext: Boolean,
    val nextCursor: NextCursorDomain,
)
