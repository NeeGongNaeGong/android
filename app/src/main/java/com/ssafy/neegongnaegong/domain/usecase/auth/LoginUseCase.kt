package com.ssafy.neegongnaegong.domain.usecase.auth

import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(idToken: String, fcmToken: String): Flow<User> {
        return authRepository.login(idToken, fcmToken)
    }
}
