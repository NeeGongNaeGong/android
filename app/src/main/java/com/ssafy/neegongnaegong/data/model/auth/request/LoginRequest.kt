package com.ssafy.neegongnaegong.data.model.auth.request

data class LoginRequest(
    val email: String,
    val fcmToken: String,
)