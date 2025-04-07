package com.ssafy.neegongnaegong.domain.usecase.auth

import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, fcmToken: String): Flow<User> {
        return authRepository.login(email, fcmToken)
    }
}