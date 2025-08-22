package com.ssafy.neegongnaegong.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface LocalUpdateDataSource {
    fun getSkippedVersionCode(): Flow<Int>

    suspend fun saveSkippedVersionCode(versionCode: Int)
}
