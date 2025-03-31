package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.safeApiCall
import com.ssafy.neegongnaegong.data.model.toFlow
import com.ssafy.neegongnaegong.data.model.user.request.UpdateUserRequest
import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import com.ssafy.neegongnaegong.data.model.user.response.ValidateNicknameResponse
import com.ssafy.neegongnaegong.data.remote.UserApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkUserDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : NetworkUserDataSource {
    override suspend fun getUser(id: Long): Flow<UserDetailResponse> = safeApiCall {
        userApi.getUser(id)
    }.toFlow()

    override suspend fun validateUserNickname(nickname: String): Flow<ValidateNicknameResponse> = safeApiCall {
        userApi.validateNickname(nickname)
    }.toFlow()

    override suspend fun updateUser(request: UpdateUserRequest): Flow<Unit> = safeApiCall {
        userApi.updateUser(request)
    }.toFlow()
}
