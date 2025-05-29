package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDateTime

data class MemberStudyContentSliceKey(
    val lastCursorCreatedAt: LocalDateTime,
    val lastLearningRecordId: Long,
)
