package com.ssafy.neegongnaegong.data.model.user.response

data class ValidateNicknameResponse(
    val nickname: String,
    val isAvailable: Boolean,
)
