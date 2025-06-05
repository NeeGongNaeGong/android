package com.ssafy.neegongnaegong.data.model.user.response

data class UserPage(
    val content: List<UserResponse>,
    val hasNext: Boolean,
    val cursorCreatedAt: String?,
    val cursorId: Long?
)
