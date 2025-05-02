package com.ssafy.neegongnaegong.domain.usecase.auth

import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(
        email: String,
        nickname: String,
        profileImage: String
    ): Flow<Boolean> {
        return authRepository.register(email, nickname, profileImage)
    }
}
