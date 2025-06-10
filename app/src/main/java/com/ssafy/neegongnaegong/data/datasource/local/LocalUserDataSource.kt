package com.ssafy.neegongnaegong.data.datasource.local

import com.ssafy.neegongnaegong.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    fun saveUser(user: User)
    fun getUser(): Flow<User>
    fun clearUser()
}
