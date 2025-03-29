package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserInfo(): Flow<User>
}