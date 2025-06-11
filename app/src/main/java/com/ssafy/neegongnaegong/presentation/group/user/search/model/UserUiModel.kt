package com.ssafy.neegongnaegong.presentation.group.user.search.model

import androidx.compose.runtime.Stable

@Stable
data class UserUiModel(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImg: String,
) {
    companion object {
        fun toDefault() =
            UserUiModel(
                id = -1,
                email = "",
                nickname = "",
                profileImg = "",
            )
    }
}
