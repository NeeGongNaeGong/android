package com.ssafy.neegongnaegong.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.neegongnaegong.data.datasource.local.LocalFcmDataSource
import com.ssafy.neegongnaegong.data.datasource.local.LocalUserDataSource
import com.ssafy.neegongnaegong.data.datasource.network.NetworkUserDataSource
import com.ssafy.neegongnaegong.data.local.TokenManager
import com.ssafy.neegongnaegong.data.mapper.user.UserMapper.toDomain
import com.ssafy.neegongnaegong.data.model.user.request.UpdateFcmTokenRequest
import com.ssafy.neegongnaegong.data.model.user.request.UpdateUserRequest
import com.ssafy.neegongnaegong.data.model.user.response.UnReadNotificationResponse
import com.ssafy.neegongnaegong.data.model.user.response.UserResponse
import com.ssafy.neegongnaegong.data.paging.UserPagingSource
import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val localUserDataSource: LocalUserDataSource,
        private val localFcmDataSource: LocalFcmDataSource,
        private val networkUserDataSource: NetworkUserDataSource,
        private val pagingSourceFactory: UserPagingSource.Factory,
        private val tokenManager: TokenManager,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : UserRepository {
        override fun getUser(): Flow<User> =
            localUserDataSource
                .getUser()
                .flowOn(ioDispatcher)

        override fun validateNickname(nickname: String): Flow<Boolean> =
            networkUserDataSource
                .validateUserNickname(nickname = nickname)
                .map { it.isAvailable }
                .flowOn(context = ioDispatcher)

        override fun updateNickname(nickname: String): Flow<Unit> =
            flow {
                val user = localUserDataSource.getUser().first()
                val userRequest = UpdateUserRequest(nickName = nickname, profileImg = user.profileImg)
                networkUserDataSource.updateUser(request = userRequest).firstOrNull()

                val updatedUser = user.copy(nickname = nickname)
                localUserDataSource.saveUser(user = updatedUser)
            }

        override suspend fun getUser(id: Long): Flow<User> =
            withContext(ioDispatcher) {
                networkUserDataSource.getUser(id).map { it.toDomain() }
            }

        override fun updateProfileImage(profileImg: String): Flow<Unit> =
            flow {
                val user: User = localUserDataSource.getUser().first()
                val userRequest = UpdateUserRequest(nickName = user.nickname, profileImg = profileImg)
                networkUserDataSource.updateUser(request = userRequest).firstOrNull()

                val updatedUser = user.copy(profileImg = profileImg)
                localUserDataSource.saveUser(user = updatedUser)
                emit(value = Unit)
            }.flowOn(context = ioDispatcher)

        override suspend fun updateFcmToken(fcmToken: String?): Unit =
            withContext(ioDispatcher) {
                val request =
                    UpdateFcmTokenRequest(fcmToken = fcmToken ?: localFcmDataSource.getFcmToken())
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

        override fun searchUser(userName: String): Flow<PagingData<User>> =
            Pager(
                config = PagingConfig(pageSize = USER_PAGING_SIZE),
                pagingSourceFactory = { pagingSourceFactory.create(userName = userName) },
            ).flow.flowOn(context = ioDispatcher).map { pagingData: PagingData<UserResponse> ->
                pagingData.toDomain()
            }

        override fun findUnReadNotification(): Flow<Boolean> =
            networkUserDataSource
                .findUnReadNotification()
                .map { response: UnReadNotificationResponse -> response.hasUnread }
                .flowOn(context = ioDispatcher)

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun logout(): Flow<Unit> =
            networkUserDataSource
                .logout()
                .onEach {
                    tokenManager.clearToken()
                    localUserDataSource.clearUser()
                }.flowOn(context = ioDispatcher)

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun withdraw(): Flow<Unit> =
            networkUserDataSource
                .withdraw()
                .onEach {
                    tokenManager.clearToken()
                    localUserDataSource.clearUser()
                }.flowOn(context = ioDispatcher)

        override fun saveProfileImageWarningAcceptedAt(time: Long) =
            localUserDataSource
                .saveProfileImageWarningAcceptedAt(time = time)

        override fun getProfileImageWarningAcceptedAt(): Flow<Long> =
            localUserDataSource
                .getProfileImageWarningAcceptedAt()
                .flowOn(context = ioDispatcher)

        companion object {
            private const val USER_PAGING_SIZE = 30
        }
    }
