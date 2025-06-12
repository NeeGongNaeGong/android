package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveProfileImageWarningAcceptedAtUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        operator fun invoke(): Flow<Unit> =
            flow {
                val currentTime: Long = System.currentTimeMillis()
                userRepository.saveProfileImageWarningAcceptedAt(time = currentTime)
                emit(value = Unit)
            }
    }
