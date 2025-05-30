package com.ssafy.neegongnaegong.data.model.studygroup.response

import java.time.LocalDateTime

data class MemberWeeklyStudyContentBySliceResponse(
    val content: List<StudyContentResponse>,
    val hasNext: Boolean,
    val cursorCreatedAt: LocalDateTime,
    val cursorId: Long,
)
