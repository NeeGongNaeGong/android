package com.ssafy.neegongnaegong.data.local

enum class TokenType(val type: String) {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token")
}

interface TokenManager {
    fun saveToken(tokenType: TokenType, token: String)
    fun getToken(tokenType: TokenType): String?
    fun clearToken()
}
