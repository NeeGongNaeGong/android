package com.ssafy.neegongnaegong.domain.model.studygroup

data class StudyGroupNoticeListRequest(
    val studyGroupId: Long,
    val cursorId: Long?,
    val size: Int = 10,
)
