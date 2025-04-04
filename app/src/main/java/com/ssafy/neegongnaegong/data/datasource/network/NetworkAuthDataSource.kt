package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.auth.request.LoginRequest
import com.ssafy.neegongnaegong.data.model.auth.request.RegisterRequest
import com.ssafy.neegongnaegong.data.model.auth.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface NetworkAuthDataSource {
    suspend fun login(request: LoginRequest): Flow<LoginResponse>

    suspend fun register(request: RegisterRequest): Flow<Long>

    suspend fun reissue(refreshToken: String): Flow<LoginResponse>
}
