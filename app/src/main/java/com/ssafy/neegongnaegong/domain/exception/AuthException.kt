package com.ssafy.neegongnaegong.domain.exception

import java.io.IOException

sealed class AuthException(override val message: String) : IOException(message) {
    companion object {
        const val INVALID_TOKEN_ERROR = "인증이 만료되었습니다"
    }

    class InvalidTokenException : AuthException(INVALID_TOKEN_ERROR)
}
