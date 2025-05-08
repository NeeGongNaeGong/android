package com.ssafy.neegongnaegong.data.datasource.local

import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.neegongnaegong.data.local.LocalStorageManager
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocalFcmDataSourceImpl @Inject constructor(
    private val localStorageManager: LocalStorageManager
) : LocalFcmDataSource {
    override suspend fun getFcmToken(): String = suspendCancellableCoroutine { continuation ->
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                continuation.resume(token)
            } else {
                continuation.resumeWithException(task.exception ?: Exception("Unknown error"))
            }
        }
    }

    override suspend fun getUpdateFcmTokenState(): Boolean {
        return localStorageManager.getData<Boolean>("fcm") ?: true
    }

    override suspend fun setUpdateFcmTokenState(state: Boolean) {
        localStorageManager.saveData("fcm", state)
    }
}
