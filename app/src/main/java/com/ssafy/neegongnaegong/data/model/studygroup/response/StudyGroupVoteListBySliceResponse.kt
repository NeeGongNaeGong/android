package com.ssafy.neegongnaegong.data.model.studygroup.response

import java.time.LocalDateTime

data class StudyGroupVoteListBySliceResponse(
    val content: List<StudyGroupVoteHistoryResponse>,
    val hasNext: Boolean,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long,
)
