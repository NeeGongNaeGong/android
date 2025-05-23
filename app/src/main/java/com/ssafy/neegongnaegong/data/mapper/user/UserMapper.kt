package com.ssafy.neegongnaegong.data.mapper.user

import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import com.ssafy.neegongnaegong.domain.model.User

internal object UserMapper {
    fun UserDetailResponse.toDomain() = User(
        id = id,
        email = email,
        nickname = nickname,
        profileImg = profileImg
    )
}
