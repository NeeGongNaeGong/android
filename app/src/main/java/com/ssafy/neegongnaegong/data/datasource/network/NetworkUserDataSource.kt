package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.user.request.UpdateUserRequest
import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import com.ssafy.neegongnaegong.data.model.user.response.ValidateNicknameResponse
import kotlinx.coroutines.flow.Flow

interface NetworkUserDataSource {
    suspend fun getUser(id: Long): Flow<UserDetailResponse>
    suspend fun validateUserNickname(nickname: String): Flow<ValidateNicknameResponse>
    suspend fun updateUser(request: UpdateUserRequest): Flow<Unit>
}
