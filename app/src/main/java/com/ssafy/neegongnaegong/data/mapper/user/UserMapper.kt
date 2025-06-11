package com.ssafy.neegongnaegong.data.mapper.user

import androidx.paging.PagingData
import androidx.paging.map
import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import com.ssafy.neegongnaegong.data.model.user.response.UserResponse
import com.ssafy.neegongnaegong.domain.model.User

internal object UserMapper {
    fun UserDetailResponse.toDomain() =
        User(
            id = id,
            email = email,
            nickname = nickname,
            profileImg = profileImg ?: "",
        )

    fun UserResponse.toDomain() =
        User(
            id = id,
            email = email,
            nickname = nickName,
            profileImg = profileImg ?: "",
        )

    fun PagingData<UserResponse>.toDomain(): PagingData<User> = map { it.toDomain() }
}
