package com.ssafy.neegongnaegong.domain.model.studygroup

data class StudyGroupVoteListRequest(
    val studyGroupId: Long,
    val cursorValue: String?,
    val cursorId: Long?,
    val size: Int = 10,
)
