package com.ssafy.neegongnaegong.presentation.profile.data

import com.ssafy.neegongnaegong.domain.model.User

internal object ProfileMapper {
    fun User.toUiModel() = ProfileUiModel(
        email = email,
        nickname = nickname,
        profileImg = profileImg
    )
}
