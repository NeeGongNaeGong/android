package com.ssafy.neegongnaegong.data.remote.authenticator

import com.ssafy.neegongnaegong.data.local.TokenManager
import com.ssafy.neegongnaegong.data.local.TokenType
import com.ssafy.neegongnaegong.data.remote.AuthApi
import com.ssafy.neegongnaegong.domain.exception.AuthException
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class ReissueAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApi: AuthApi
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        // TODO: token 401인지 확인하기
        synchronized(this) {
            val newAccessToken = runBlocking { fetchNewAccessToken() } ?: throw AuthException.InvalidTokenException()

            // TODO: 만약 새로 받아온 accessToken으로도 실패하면 어떻게 할지 고민

            return response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        }
    }

    private suspend fun fetchNewAccessToken(): String? {
        val refreshToken = tokenManager.getToken(TokenType.ACCESS_TOKEN) ?: return null

        return runCatching {
            authApi.reissue(refreshToken)
        }.fold(
            onSuccess = { response ->
                response.takeIf { it.isSuccessful }?.body()?.data?.createJwt?.also {
                    tokenManager.saveToken(TokenType.ACCESS_TOKEN, it.accessToken.removePrefix("Bearer "))
                    tokenManager.saveToken(TokenType.REFRESH_TOKEN, it.refreshToken.removePrefix("Bearer "))
                }?.accessToken
            },
            onFailure = { null }
        )
    }
}
