package com.ssafy.neegongnaegong.data.datasource.local

import com.ssafy.neegongnaegong.data.local.LocalStorageManager
import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val localStorageManager: LocalStorageManager
) : LocalUserDataSource {
    override fun saveUser(user: User): Unit = localStorageManager.saveData("user", user)

    override fun clearUser(): Unit = localStorageManager.removeData("user")

    override fun getUser(): Flow<User> = localStorageManager
        .getDataFlow<User>("user", User::class.java)
        .map { user: User? -> user ?: User.default() }
}
