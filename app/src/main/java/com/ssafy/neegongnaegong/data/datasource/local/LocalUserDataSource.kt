package com.ssafy.neegongnaegong.data.datasource.local

import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    suspend fun saveUser(user: User): Flow<Boolean>
    suspend fun getUser(): Flow<User>
}
