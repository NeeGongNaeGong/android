package com.ssafy.neegongnaegong.data.model.auth.response

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String,
)
