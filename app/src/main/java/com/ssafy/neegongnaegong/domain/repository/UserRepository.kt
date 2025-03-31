package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(): Flow<User>
    suspend fun getUser(id: Long): Flow<User>
    suspend fun validateNickname(nickname: String): Flow<Boolean>
    suspend fun updateNickname(nickname: String): Flow<Unit>
    suspend fun updateProfileImage(profileImage: String): Flow<Unit>
}
