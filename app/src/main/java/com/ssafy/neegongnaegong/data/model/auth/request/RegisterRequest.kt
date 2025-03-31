package com.ssafy.neegongnaegong.data.model.auth.request

data class RegisterRequest(
    val email: String,
    val nickname: String,
    val profileImage: String,
)
