package com.ssafy.neegongnaegong.data.datasource.local

import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    fun saveUser(user: User): Flow<Boolean>
    suspend fun clearUser(): Flow<Boolean>
    fun getUser(): Flow<User>
}
