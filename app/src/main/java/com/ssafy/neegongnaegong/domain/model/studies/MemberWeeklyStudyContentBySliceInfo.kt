package com.ssafy.neegongnaegong.domain.model.studies

import java.time.LocalDateTime

data class MemberWeeklyStudyContentBySliceInfo(
    val content: List<StudyContentInfo>,
    val hasNext: Boolean,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long
)
