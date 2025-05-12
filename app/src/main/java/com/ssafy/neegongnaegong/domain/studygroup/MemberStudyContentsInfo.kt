package com.ssafy.neegongnaegong.domain.studygroup

import java.time.LocalDateTime

data class MemberStudyContentsInfo(
    val studyGroupId: Long,
    val userId: Long,
    val lastCursorCreatedAt: LocalDateTime,
    val lastLearningRecordId: Long,
    val size: Int = 10
)
