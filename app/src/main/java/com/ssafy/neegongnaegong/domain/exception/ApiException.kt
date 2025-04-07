package com.ssafy.neegongnaegong.domain.exception

import java.io.IOException

sealed class ApiException(message: String?) : IOException(message) {
    companion object {
        const val UNKNOWN_ERROR = "알수 없는 에러 발생"
        const val NETWORK_ERROR = "네트워크 에러 발생"
        const val SERVER_ERROR = "서버 에러 발생"

        fun fromCode(code: Int, message: String? = null): ApiException = when (code) {
            in 400..499 -> ClientException(message)
            in 500..599 -> ServerException(message)
            else -> UnknownException(message)
        }
    }

    class UnknownException(message: String? = null) : ApiException(message ?: UNKNOWN_ERROR)
    class NetworkException : ApiException(NETWORK_ERROR)
    class ServerException(message: String? = null) : ApiException(message ?: SERVER_ERROR)
    class ClientException(message: String?) : ApiException(message)
}
