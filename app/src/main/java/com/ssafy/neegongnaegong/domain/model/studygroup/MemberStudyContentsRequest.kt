package com.ssafy.neegongnaegong.domain.model.studygroup

data class MemberStudyContentsRequest(
    val studyGroupId: Long,
    val userId: Long,
    val cursorValue: String?,
    val cursorId: Long?,
    val size: Int = 10,
)
