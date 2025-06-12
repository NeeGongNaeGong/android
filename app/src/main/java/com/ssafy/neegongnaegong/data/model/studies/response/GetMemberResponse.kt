package com.ssafy.neegongnaegong.data.model.studies.response

data class GetMemberResponse(
    val userId: Long,
    val name: String,
    val profileImg: String,
    val groupRole: String,
)
