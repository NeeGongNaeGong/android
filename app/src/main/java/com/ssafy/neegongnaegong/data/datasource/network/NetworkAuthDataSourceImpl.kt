package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.auth.request.LoginRequest
import com.ssafy.neegongnaegong.data.model.auth.request.RegisterRequest
import com.ssafy.neegongnaegong.data.model.auth.response.LoginResponse
import com.ssafy.neegongnaegong.data.remote.AuthApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkAuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : NetworkAuthDataSource {
    override suspend fun login(request: LoginRequest): Flow<LoginResponse> = apiFlow {
        authApi.login(request)
    }

    override suspend fun register(request: RegisterRequest): Flow<Long> = apiFlow {
        authApi.register(request)
    }

    override suspend fun reissue(refreshToken: String): Flow<LoginResponse> = apiFlow {
        authApi.reissue(refreshToken)
    }
}
