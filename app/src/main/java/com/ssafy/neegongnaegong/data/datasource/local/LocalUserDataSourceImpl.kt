package com.ssafy.neegongnaegong.data.datasource.local

import com.ssafy.neegongnaegong.data.local.LocalStorageManager
import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val localStorageManager: LocalStorageManager
) : LocalUserDataSource {
    override suspend fun saveUser(user: User): Flow<Boolean> = flow {
        runCatching { localStorageManager.saveData("user", user) }.fold(
            onSuccess = { emit(true) },
            onFailure = { emit(false) }
        )
    }

    override suspend fun getUser(): Flow<User> = flow {
        localStorageManager.getData<User>("user")?.let { emit(it) }
    }
}
