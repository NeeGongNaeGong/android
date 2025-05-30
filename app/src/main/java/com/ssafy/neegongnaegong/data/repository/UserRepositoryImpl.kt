package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.local.LocalFcmDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalUserDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkUserDataSource
import com.ssafy.neegongnaegong.data.mapper.user.UserMapper.toDomain
import com.ssafy.neegongnaegong.data.model.user.request.UpdateFcmTokenRequest
import com.ssafy.neegongnaegong.data.model.user.request.UpdateUserRequest
import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val localUserDataSource: LocalUserDataSource,
        private val localFcmDataSource: LocalFcmDataSource,
        private val networkUserDataSource: NetworkUserDataSource,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : UserRepository {
        override suspend fun getUser(): Flow<User> =
            withContext(ioDispatcher) {
                localUserDataSource.getUser()
            }

        override suspend fun getUser(id: Long): Flow<User> =
            withContext(ioDispatcher) {
                networkUserDataSource.getUser(id).map { it.toDomain() }
            }

        override suspend fun validateNickname(nickname: String): Flow<Boolean> =
            withContext(ioDispatcher) {
                networkUserDataSource.validateUserNickname(nickname).map { it.isAvailable }
            }

        override suspend fun updateNickname(nickname: String): Flow<Unit> =
            withContext(ioDispatcher) {
                val user = localUserDataSource.getUser().first()
                networkUserDataSource.updateUser(UpdateUserRequest(nickname = nickname, profileImg = user.profileImg))
            }

        override suspend fun updateProfileImage(profileImage: String): Flow<Unit> =
            withContext(ioDispatcher) {
                val user = localUserDataSource.getUser().first()
                networkUserDataSource.updateUser(UpdateUserRequest(nickname = user.nickname, profileImg = profileImage))
            }

        override suspend fun updateFcmToken(fcmToken: String?) =
            withContext(ioDispatcher) {
                val request = UpdateFcmTokenRequest(fcmToken = fcmToken ?: localFcmDataSource.getFcmToken())
                try {
                    networkUserDataSource.updateFcmToken(request)
                    localFcmDataSource.setUpdateFcmTokenState(true)
                } catch (e: Exception) {
                    localFcmDataSource.setUpdateFcmTokenState(false)
                }
            }

        override suspend fun checkUpdateFcmTokenState(): Boolean =
            withContext(ioDispatcher) {
                localFcmDataSource.getUpdateFcmTokenState()
            }
    }
