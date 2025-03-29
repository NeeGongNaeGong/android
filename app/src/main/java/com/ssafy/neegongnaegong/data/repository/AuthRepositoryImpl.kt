package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.local.LocalUserDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkAuthDataSource
import com.ssafy.neegongnaegong.data.local.TokenManager
import com.ssafy.neegongnaegong.data.local.TokenType
import com.ssafy.neegongnaegong.data.model.auth.request.LoginRequest
import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val localUserDataSource: LocalUserDataSource,
    private val networkAuthDataSource: NetworkAuthDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {
    override suspend fun login(
        email: String,
        fcmToken: String,
    ): Flow<User> = withContext(ioDispatcher) {
        networkAuthDataSource.login(LoginRequest(email = email, fcmToken = fcmToken))
            .onEach { user ->
                tokenManager.saveToken(TokenType.ACCESS_TOKEN, user.createJwt.accessToken)
                tokenManager.saveToken(TokenType.REFRESH_TOKEN, user.createJwt.refreshToken)
                localUserDataSource.saveUser(user.userDetailedInquiryResponse.toDomain())
            }.map { user ->
                user.userDetailedInquiryResponse.toDomain()
            }
    }

    override suspend fun register(id: String, password: String, name: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Flow<Boolean> {
        TODO("Not yet implemented")
    }
}
