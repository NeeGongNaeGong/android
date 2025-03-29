package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkUserDataSourceImpl @Inject constructor(

): NetworkUserDataSource {
    override suspend fun getUser(): Flow<UserDetailResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun validateUserNickname(nickname: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserNickname(nickname: String): Flow<UserDetailResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserProfileImg(image: String): Flow<UserDetailResponse> {
        TODO("Not yet implemented")
    }
}