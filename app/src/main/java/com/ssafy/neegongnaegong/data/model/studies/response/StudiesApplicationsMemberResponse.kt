package com.ssafy.neegongnaegong.data.model.studies.response

data class StudiesApplicationsMemberResponse(
    val memberId: Long,
    val userId: Long,
    val name: String,
    val profileImageUrl: String,
    val cursorId: Long,
)
