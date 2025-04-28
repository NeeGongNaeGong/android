package com.ssafy.neegongnaegong.domain.exception

import java.io.IOException

sealed class ApiException(override val message: String) : IOException(message) {
    class UnknownException(message: String? = null) : ApiException(message ?: UNKNOWN_ERROR)

    class NetworkException : ApiException(NETWORK_ERROR)

    class FileNotFoundException(message: String? = null) : ApiException(message ?: FILE_NOT_FOUND_ERROR)

    class ServerException(message: String? = null) : ApiException(message ?: SERVER_ERROR)

    class ClientException(message: String? = null) : ApiException(message ?: CLIENT_ERROR)

    companion object {
        const val UNKNOWN_ERROR = "알수 없는 에러 발생"
        const val NETWORK_ERROR = "네트워크 에러 발생"
        const val SERVER_ERROR = "서버 에러 발생"
        const val FILE_NOT_FOUND_ERROR = "파일을 찾을 수 없습니다"
        const val CLIENT_ERROR = "잘못된 요청입니다"

        fun fromCode(code: Int, message: String? = null): ApiException = when (code) {
            in 400..499 -> ClientException(message)
            in 500..599 -> ServerException(message)
            else -> UnknownException(message)
        }

        fun fromCommonException(error: Throwable) = when (error) {
            is AuthException, is ApiException -> error
            is java.io.FileNotFoundException -> FileNotFoundException()
            is IOException -> NetworkException()
            else -> UnknownException(error.message)
        }
    }
}
