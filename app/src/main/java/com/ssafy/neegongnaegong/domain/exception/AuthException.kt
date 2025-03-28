package com.ssafy.neegongnaegong.domain.exception

import java.io.IOException

sealed class AuthException(message: String?) : IOException(message) {
    companion object {
        const val INVALID_TOKEN_ERROR = "토큰 에러 발생"
    }

    class InvalidTokenException : AuthException(INVALID_TOKEN_ERROR)
}