package com.ssafy.neegongnaegong.data.model.user.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    val id: Long,
    val email: String,
    @SerializedName("nickName")
    val nickname: String,
    val profileImg: String?,
)
