package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.local.LocalFcmDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalUserDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkAuthDataSource
import com.ssafy.neegongnaegong.data.local.TokenManager
import com.ssafy.neegongnaegong.data.local.TokenType
import com.ssafy.neegongnaegong.data.mapper.user.UserMapper.toDomain
import com.ssafy.neegongnaegong.data.model.auth.request.LoginRequest
import com.ssafy.neegongnaegong.data.model.auth.request.RefreshRequest
import com.ssafy.neegongnaegong.data.model.auth.request.RegisterRequest
import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val localUserDataSource: LocalUserDataSource,
    private val localFcmDataSource: LocalFcmDataSource,
    private val networkAuthDataSource: NetworkAuthDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {
    override suspend fun login(idToken: String): Flow<User> = withContext(ioDispatcher) {
        val fcmToken = localFcmDataSource.getFcmToken()
        networkAuthDataSource.login(LoginRequest(idToken = idToken, fcmToken = fcmToken))
            .onEach { user ->
                tokenManager.saveToken(TokenType.ACCESS_TOKEN, user.createJwt.accessToken.removePrefix("Bearer "))
                tokenManager.saveToken(TokenType.REFRESH_TOKEN, user.createJwt.refreshToken.removePrefix("Bearer "))
                localUserDataSource.saveUser(user.userDetailedInquiryResponse.toDomain())
                localFcmDataSource.setUpdateFcmTokenState(true)
            }.map { user ->
                user.userDetailedInquiryResponse.toDomain()
            }
    }

    override suspend fun register(
        email: String,
        nickname: String,
        profileImage: String
    ): Flow<Boolean> = withContext(ioDispatcher) {
        networkAuthDataSource.register(
            RegisterRequest(
                email = email,
                nickname = nickname,
                profileImage = profileImage
            )
        ).map { true }
    }

    override suspend fun logout(): Flow<Boolean> = withContext(ioDispatcher) {
        flow {
            tokenManager.clearToken()
            localUserDataSource.clearUser()
            emit(true)
        }
    }

    override suspend fun reissue(): Flow<Boolean> = withContext(ioDispatcher) {
        flow {
            val existRefreshToken = tokenManager.getToken(TokenType.REFRESH_TOKEN)
                ?: return@flow emit(false)

            val request = RefreshRequest("Bearer $existRefreshToken")

            networkAuthDataSource.reissue(request).collect { response ->
                with(response.createJwt) {
                    tokenManager.saveToken(TokenType.ACCESS_TOKEN, accessToken)
                    tokenManager.saveToken(TokenType.REFRESH_TOKEN, refreshToken)
                }
                emit(true)
            }
        }
    }
}
