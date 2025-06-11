package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDateTime

data class StudyGroupVoteListRequest(
    val studyGroupId: Long,
    val cursorTime: LocalDateTime?,
    val cursorId: Long?,
    val size: Int = 10,
)
