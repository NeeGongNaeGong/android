package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UpdateNicknameUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(nickname: String): Flow<Unit> {
        return userRepository.updateNickname(nickname)
    }
}
