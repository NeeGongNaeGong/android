package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ValidateNicknameUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(nickname: String): Flow<Boolean> {
        return userRepository.validateNickname(nickname)
    }
}
