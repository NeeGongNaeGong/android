package com.ssafy.neegongnaegong.domain.model

data class User(
    val id: Long,
    val nickname: String,
    val profileImg: String,
) {
    companion object {
        fun default() =
            User(
                id = 0L,
                nickname = "",
                profileImg = "",
            )
    }
}
