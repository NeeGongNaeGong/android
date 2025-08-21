package com.ssafy.neegongnaegong.data.repository

import com.google.android.play.core.appupdate.AppUpdateManager
import com.ssafy.neegongnaegong.data.datasource.local.LocalUpdateDataSource
import com.ssafy.neegongnaegong.data.mapper.update.AppUpdateInfoMapper.toDomain
import com.ssafy.neegongnaegong.domain.model.update.AppVersionInfo
import com.ssafy.neegongnaegong.domain.repository.UpdateRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateRepositoryImpl
    @Inject
    constructor(
        private val appUpdateManager: AppUpdateManager,
        private val localUpdateDataSource: LocalUpdateDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : UpdateRepository {
        override suspend fun getAppUpdateInfo(): AppVersionInfo =
            runCatching {
            /*
            플레이 스토어에서 다운 받은 거 아니면 crash 발생 => Android Studio에서 빌드 돌리면 Crash
            아니면 onAddSuccessListener로 성공했을 떄 로직만 작성하고 해야 해서 run Catching으로 감싸도록 수정
             */
                appUpdateManager.appUpdateInfo.await().toDomain()
            }.getOrElse { _ ->
                AppVersionInfo.Unavailable
            }

        override fun getSkippedVersionCode(): Flow<Int> = localUpdateDataSource.getSkippedVersionCode().flowOn(ioDispatcher)

        override suspend fun saveSkippedVersionCode(versionCode: Int) =
            withContext(ioDispatcher) {
                localUpdateDataSource.saveSkippedVersionCode(versionCode)
            }
    }
