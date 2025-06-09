package com.ssafy.neegongnaegong.data.model.user.response

data class UserResponse(
    val id: Long,
    val email: String,
    val nickName: String,
    val profileImg: String?,
    val cursorId: Long,
    val cursorCreatedAt: String
)
