package com.ssafy.neegongnaegong.data.remote.utils

interface TokenManager {
    fun saveToken(tokenType: String, token: String)
    fun getToken(tokenType: String): String?
    fun clearToken()
}