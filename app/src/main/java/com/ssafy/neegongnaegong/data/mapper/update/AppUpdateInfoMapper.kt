package com.ssafy.neegongnaegong.data.mapper.update

import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.model.UpdateAvailability
import com.ssafy.neegongnaegong.domain.model.update.AppVersionInfo

internal object AppUpdateInfoMapper {
    fun AppUpdateInfo.toDomain() =
        AppVersionInfo(
            availableVersionCode = availableVersionCode(),
            updateAvailability = updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE,
        )
}
