package com.ssafy.neegongnaegong.data.model.auth.response

data class LoginResponse(
    val createJwt: JwtResponse,
    val userDetailedInquiryResponse: UserDetailResponse
)
