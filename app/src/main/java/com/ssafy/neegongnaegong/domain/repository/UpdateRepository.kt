package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.update.AppVersionInfo
import kotlinx.coroutines.flow.Flow

interface UpdateRepository {
    // 플레이 스토어에서 업데이트 정보 가져오기
    suspend fun getAppUpdateInfo(): AppVersionInfo

    // 사용자가 건너뛴 버전 정보 가져오기
    fun getSkippedVersionCode(): Flow<Int>

    // 사용자가 건너뛴 버전 저장하기
    suspend fun saveSkippedVersionCode(versionCode: Int)
}
