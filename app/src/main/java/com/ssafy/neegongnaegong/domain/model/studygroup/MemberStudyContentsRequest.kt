package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDateTime

data class MemberStudyContentsRequest(
    val studyGroupId: Long,
    val userId: Long,
    val cursorCreatedAt: LocalDateTime?,
    val cursorId: Long?,
    val size: Int = 10,
)
