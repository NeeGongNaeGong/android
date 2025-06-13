package com.ssafy.neegongnaegong.presentation.profile.data

import androidx.compose.runtime.Stable

@Stable
data class ProfileUiModel(
    val id: Long,
    val email: String,
    val nickname: String,
    val profileImg: String,
    val hasUnReadNotification: Boolean,
    val shouldShowProfileImageWarningInfo: Boolean,
) {
    companion object {
        fun default() =
            ProfileUiModel(
                id = 0L,
                email = "",
                nickname = "",
                profileImg = "",
                hasUnReadNotification = false,
                shouldShowProfileImageWarningInfo = false,
            )
    }
}
