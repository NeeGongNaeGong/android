package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, fcmToken: String): Flow<User>
    suspend fun register(id: String, password: String, name: String): Flow<Boolean>
    suspend fun logout(): Flow<Boolean>
}