package com.ssafy.neegongnaegong.data.model.auth.request

data class LoginRequest(
    val idToken: String,
    val fcmToken: String,
)
