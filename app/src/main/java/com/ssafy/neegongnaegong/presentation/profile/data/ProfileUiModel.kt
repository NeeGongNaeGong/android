package com.ssafy.neegongnaegong.presentation.profile.data

import androidx.compose.runtime.Stable

@Stable
data class ProfileUiModel(
    val email: String,
    val nickname: String,
    val profileImg: String,
) {
    companion object {
        fun default() = ProfileUiModel(
            email = "",
            nickname = "",
            profileImg = ""
        )
    }
}
