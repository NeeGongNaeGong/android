package com.ssafy.neegongnaegong.domain.usecase.update

import com.ssafy.neegongnaegong.domain.model.update.AppVersionInfo
import com.ssafy.neegongnaegong.domain.repository.UpdateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckForUpdateUseCase
    @Inject
    constructor(
        private val updateRepository: UpdateRepository,
    ) {
        operator fun invoke(): Flow<AppVersionInfo> =
            with(updateRepository) {
                // 저장된 '건너뛴 버전'을 먼저 가져옴
                getSkippedVersionCode().map { skippedVersion ->
                    // 플레이 스토어에서 업데이트 정보를 가져옴
                    val updateInfoResult = getAppUpdateInfo()
                    with(updateInfoResult) {
                        val isNewerThanSkipped = updateAvailability && availableVersionCode > skippedVersion
                        updateInfoResult.copy(updateAvailability = isNewerThanSkipped)
                    }
                }
            }
    }
