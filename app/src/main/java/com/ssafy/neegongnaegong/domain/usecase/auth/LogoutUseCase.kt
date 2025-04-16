package com.ssafy.neegongnaegong.domain.usecase.auth

import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return authRepository.logout()
    }
}
