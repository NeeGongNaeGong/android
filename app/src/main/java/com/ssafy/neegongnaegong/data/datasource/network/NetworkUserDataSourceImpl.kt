package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.user.request.UpdateFcmTokenRequest
import com.ssafy.neegongnaegong.data.model.user.request.UpdateUserRequest
import com.ssafy.neegongnaegong.data.model.user.response.UnReadNotificationResponse
import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import com.ssafy.neegongnaegong.data.model.user.response.UserPage
import com.ssafy.neegongnaegong.data.model.user.response.ValidateNicknameResponse
import com.ssafy.neegongnaegong.data.remote.UserApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : NetworkUserDataSource {
    override suspend fun getUser(id: Long): Flow<UserDetailResponse> = apiFlow {
        userApi.getUser(id)
    }

    override fun validateUserNickname(nickname: String): Flow<ValidateNicknameResponse> = apiFlow {
        userApi.validateNickname(nickname)
    }

    override fun updateUser(request: UpdateUserRequest): Flow<Unit> = apiFlow {
        userApi.updateUser(request)
    }

    override suspend fun updateFcmToken(request: UpdateFcmTokenRequest): Flow<Unit> = apiFlow {
        userApi.updateFcmToken(request)
    }

    override fun searchUsers(
        time: String?,
        cursorId: Long?,
        size: Int,
        userName: String
    ): Flow<UserPage> = apiFlow {
        userApi.findUsers(
            time = time,
            cursorId = cursorId,
            size = size,
            userName = userName
        )
    }

    override fun findUnReadNotification(): Flow<UnReadNotificationResponse> = apiFlow {
        userApi.findUnReadNotification()
    }
}
