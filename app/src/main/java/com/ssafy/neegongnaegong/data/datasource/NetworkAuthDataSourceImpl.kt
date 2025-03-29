package com.ssafy.neegongnaegong.data.datasource

import com.ssafy.neegongnaegong.data.model.auth.request.LoginRequest
import com.ssafy.neegongnaegong.data.model.auth.request.RegisterRequest
import com.ssafy.neegongnaegong.data.model.auth.response.LoginResponse
import com.ssafy.neegongnaegong.data.model.safeApiCall
import com.ssafy.neegongnaegong.data.model.toFlow
import com.ssafy.neegongnaegong.data.remote.AuthApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkAuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : NetworkAuthDataSource {
    override suspend fun login(request: LoginRequest): Flow<LoginResponse> = safeApiCall {
        authApi.login(request)
    }.toFlow()

    override suspend fun register(request: RegisterRequest): Flow<Long> = safeApiCall {
        authApi.register(request)
    }.toFlow()

    override suspend fun reissue(refreshToken: String): Flow<LoginResponse> = safeApiCall {
        authApi.reissue(refreshToken)
    }.toFlow()
}