package com.ssafy.neegongnaegong.data.remote

interface TokenManager {
    fun saveToken(tokenType: String, token: String)
    fun getToken(tokenType: String): String?
    fun clearToken()
}