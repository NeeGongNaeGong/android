package com.ssafy.neegongnaegong.data.datasource.local

import com.ssafy.neegongnaegong.data.local.LocalStorageManager
import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val localStorageManager: LocalStorageManager
) : LocalUserDataSource {
    override fun saveUser(user: User): Unit = localStorageManager.saveData(USER, user)

    override fun clearUser(): Unit = localStorageManager.removeData(USER)

    override fun getUser(): Flow<User> = localStorageManager
        .getDataFlow<User>(USER, User::class.java)
        .map { user: User? -> user ?: User.default() }

    override fun saveProfileImageWarningAcceptedAt(time: Long) = localStorageManager
        .saveData(IMAGE_WARNING, time)

    override fun getProfileImageWarningAcceptedAt(): Flow<Long> = localStorageManager
        .getDataFlow(IMAGE_WARNING, Long::class.java)
        .map { it ?: 0L }

    companion object {
        private const val USER = "user"
        private const val IMAGE_WARNING = "profile_image_warning_accepted_at"
    }
}
