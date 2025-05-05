package com.ssafy.neegongnaegong.data.model.user.response

import com.google.gson.annotations.SerializedName
import com.ssafy.neegongnaegong.domain.model.User

data class UserDetailResponse(
    val id: Long,
    val email: String,
    @SerializedName("nickName")
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
