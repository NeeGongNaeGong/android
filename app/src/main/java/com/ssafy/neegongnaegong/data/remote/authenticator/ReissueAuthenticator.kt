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

            return response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        }
    }

    private suspend fun fetchNewAccessToken(): String? {
        val existingRefreshToken = tokenManager.getToken(TokenType.REFRESH_TOKEN) ?: return null

        return authApi.reissue(existingRefreshToken).getOrThrow().data.let {
            with(it.createJwt) {
                tokenManager.saveToken(TokenType.ACCESS_TOKEN, accessToken)
                tokenManager.saveToken(TokenType.REFRESH_TOKEN, refreshToken)
            }
            tokenManager.getToken(TokenType.ACCESS_TOKEN)
        }
    }
}
