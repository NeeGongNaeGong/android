package com.ssafy.neegongnaegong.data.datasource.local

interface LocalFcmDataSource {
    suspend fun getFcmToken(): String
    suspend fun getUpdateFcmTokenState(): Boolean
    suspend fun setUpdateFcmTokenState(state: Boolean)
}
