package com.ssafy.neegongnaegong.data.model.auth.response

import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse

data class LoginResponse(
    val createJwt: JwtResponse,
    val userDetailedInquiryResponse: UserDetailResponse
)
