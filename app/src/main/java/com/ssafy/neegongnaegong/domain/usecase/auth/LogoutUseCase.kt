package com.ssafy.neegongnaegong.domain.usecase.auth

import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Flow<Boolean> {
        return authRepository.logout()
    }
}
