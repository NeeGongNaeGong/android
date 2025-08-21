package com.ssafy.neegongnaegong.domain.model.update

data class AppVersionInfo(
    val availableVersionCode: Int,
    val updateAvailability: Boolean,
) {
    companion object {
        val Unavailable = AppVersionInfo(availableVersionCode = -1, updateAvailability = false)
    }
}
