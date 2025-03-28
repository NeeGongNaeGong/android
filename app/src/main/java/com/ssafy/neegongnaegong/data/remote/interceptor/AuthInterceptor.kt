package com.ssafy.neegongnaegong.data.remote.interceptor

import com.ssafy.neegongnaegong.BuildConfig
import com.ssafy.neegongnaegong.data.remote.utils.TokenManager
import com.ssafy.neegongnaegong.domain.exception.AuthException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val token = tokenManager.getToken(BuildConfig.TOKEN_TYPE_ACCESS) ?: throw AuthException.InvalidTokenException()

        val request = request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        proceed(request)
    }
}
