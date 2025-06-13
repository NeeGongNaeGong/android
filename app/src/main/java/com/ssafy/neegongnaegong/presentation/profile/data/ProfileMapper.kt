package com.ssafy.neegongnaegong.presentation.profile.data

import com.ssafy.neegongnaegong.domain.model.User

internal object ProfileMapper {
    fun User.toUiModel(
        hasUnReadNotification: Boolean,
        shouldShowProfileImageWarningInfo: Boolean,
    ) = ProfileUiModel(
        id = id,
        email = email,
        nickname = nickname,
        profileImg = profileImg,
        hasUnReadNotification = hasUnReadNotification,
        shouldShowProfileImageWarningInfo = shouldShowProfileImageWarningInfo,
    )
}
