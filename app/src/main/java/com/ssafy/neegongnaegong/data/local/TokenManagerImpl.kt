package com.ssafy.neegongnaegong.data.local

import javax.inject.Inject

class TokenManagerImpl
    @Inject
    constructor(
        private val localStorageManager: LocalStorageManager,
    ) : TokenManager {
        override fun saveToken(
            tokenType: TokenType,
            token: String,
        ) {
            localStorageManager.saveData(tokenType.type, token.removePrefix("Bearer "))
        }

        override fun getToken(tokenType: TokenType): String? {
            return localStorageManager.getData(tokenType.type)
        }

        override fun clearToken() {
            localStorageManager.removeData(TokenType.ACCESS_TOKEN.type)
            localStorageManager.removeData(TokenType.REFRESH_TOKEN.type)
        }
    }
