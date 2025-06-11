package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.user.request.UpdateFcmTokenRequest
import com.ssafy.neegongnaegong.data.model.user.request.UpdateUserRequest
import com.ssafy.neegongnaegong.data.model.user.response.UnReadNotificationResponse
import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import com.ssafy.neegongnaegong.data.model.user.response.UserPage
import com.ssafy.neegongnaegong.data.model.user.response.ValidateNicknameResponse
import kotlinx.coroutines.flow.Flow

interface NetworkUserDataSource {
    fun updateUser(request: UpdateUserRequest): Flow<Unit>

    fun validateUserNickname(nickname: String): Flow<ValidateNicknameResponse>

    suspend fun getUser(id: Long): Flow<UserDetailResponse>

    suspend fun updateFcmToken(request: UpdateFcmTokenRequest): Flow<Unit>

    fun searchUsers(
        time: String?,
        cursorId: Long?,
        size: Int,
        userName: String,
    ): Flow<UserPage>

    fun findUnReadNotification(): Flow<UnReadNotificationResponse>

    fun logout(): Flow<Unit>

    fun withdraw(): Flow<Unit>
}
