package com.ssafy.neegongnaegong.data.model.studies.request

data class GetStudiesApplicationsMembersRequest(
    val studyGroupId: Long,
    val cursorId: Long?,
    val size: Int,
)
