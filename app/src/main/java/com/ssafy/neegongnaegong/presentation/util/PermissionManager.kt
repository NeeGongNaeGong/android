package com.ssafy.neegongnaegong.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionManager {
    /**
     * 앱에 필요한 권한 목록을 반환합니다.
     */
    fun getPermissions(): Array<String> {
        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        return permissions.toTypedArray()
    }

    /**
     * 모든 권한이 허용되었는지 반환합니다.
     *
     * @param context 권한을 확인할 컨텍스트
     * @param permissions 확인할 권한 목록
     * @return 모든 권한이 허용되었는지 여부
     */
    fun isPermissionsGranted(context: Context, permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * 허용되지 않은 권한 목록을 반환합니다.
     *
     * @param context 권한을 확인할 컨텍스트
     * @param permissions 확인할 권한 목록
     * @return 허용되지 않은 권한 목록
     */
    private fun getUngrantedPermissions(
        context: Context,
        permissions: Array<String>
    ): List<String> {
        return permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * 허용되지 않은 권한을 요청합니다.
     *
     * @param activity 권한을 요청할 액티비티
     * @param launcher 권한 요청 결과를 처리할 ActivityResultLauncher
     */
    fun requestPermissions(
        activity: Activity,
        launcher: ActivityResultLauncher<Array<String>>,
        permissions: Array<String>
    ) {
        val ungranted = getUngrantedPermissions(activity, permissions)
        if (ungranted.isNotEmpty()) {
            launcher.launch(ungranted.toTypedArray())
        }
    }
}
