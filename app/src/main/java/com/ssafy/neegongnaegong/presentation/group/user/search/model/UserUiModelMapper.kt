package com.ssafy.neegongnaegong.presentation.group.user.search.model

import androidx.paging.PagingData
import androidx.paging.map
import com.ssafy.neegongnaegong.domain.model.User

object UserUiModelMapper {
    fun User.toUiModel() =
        UserUiModel(
            id = id,
            nickname = nickname,
            profileImg = profileImg,
        )

    fun PagingData<User>.toUiModel() = map { it.toUiModel() }
}
