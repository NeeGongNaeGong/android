package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(idToken: String): Flow<User>
    suspend fun register(email: String, nickname: String, profileImage: String): Flow<Boolean>
    suspend fun reissue(): Flow<Boolean>
}
