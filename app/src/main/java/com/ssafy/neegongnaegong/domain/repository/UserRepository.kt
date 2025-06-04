package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User>
    fun validateNickname(nickname: String): Flow<Boolean>
    fun updateNickname(nickname: String): Flow<Unit>

    suspend fun getUser(id: Long): Flow<User>
    suspend fun updateProfileImage(profileImage: String): Flow<Unit>
    suspend fun updateFcmToken(fcmToken: String? = null)
    suspend fun checkUpdateFcmTokenState(): Boolean
}
