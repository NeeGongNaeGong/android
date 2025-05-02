package com.ssafy.neegongnaegong.domain.usecase.auth

import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class ReissueUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Flow<Boolean> {
        return authRepository.reissue()
    }
}