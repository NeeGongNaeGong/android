package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import kotlinx.coroutines.flow.Flow

interface NetworkUserDataSource {
    suspend fun getUser(): Flow<UserDetailResponse>
    suspend fun validateUserNickname(nickname: String): Flow<Boolean>
    suspend fun updateUserNickname(nickname: String): Flow<UserDetailResponse>
    suspend fun updateUserProfileImg(image: String): Flow<UserDetailResponse>
}