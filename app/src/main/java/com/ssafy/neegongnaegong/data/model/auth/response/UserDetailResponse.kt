package com.ssafy.neegongnaegong.data.model.auth.response

data class UserDetailResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImg: String,
)
