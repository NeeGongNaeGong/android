package com.ssafy.neegongnaegong.data.model.user.response

import com.ssafy.neegongnaegong.domain.model.User

data class UserDetailResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImg: String,
) {
    fun toDomain() = User(
        id = id,
        email = email,
        nickname = nickname,
        profileImg = profileImg
    )
}
