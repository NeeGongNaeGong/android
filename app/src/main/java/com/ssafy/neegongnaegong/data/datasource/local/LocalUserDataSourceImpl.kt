package com.ssafy.neegongnaegong.data.datasource.local

import com.ssafy.neegongnaegong.data.local.LocalStorageManager
import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val localStorageManager: LocalStorageManager
) : LocalUserDataSource {
    override fun saveUser(user: User): Flow<Boolean> = flow {
        runCatching { localStorageManager.saveData("user", user) }.fold(
            onSuccess = { emit(true) },
            onFailure = { emit(false) }
        )
    }

    override suspend fun clearUser(): Flow<Boolean> = flow {
        runCatching { localStorageManager.removeData("user") }.fold(
            onSuccess = { emit(true) },
            onFailure = { emit(false) }
        )
    }

    override fun getUser(): Flow<User> = localStorageManager
        .getDataFlow<User>("user", User::class.java)
        .map { user: User? -> user ?: User.default() }
}
