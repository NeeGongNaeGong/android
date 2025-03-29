package com.ssafy.neegongnaegong.data.local

import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(
   private val localStorageManager: LocalStorageManager
) : TokenManager {
    override fun saveToken(tokenType: TokenType, token: String) {
       localStorageManager.saveData(tokenType.type, token)
    }

    override fun getToken(tokenType: TokenType): String? {
        return localStorageManager.getData(tokenType.type)
    }

    override fun clearToken() {
        runBlocking { localStorageManager.clearData() }
    }
}
