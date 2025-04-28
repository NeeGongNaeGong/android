package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UpdateProfileImageUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(profileImage: String): Flow<Unit> {
        return userRepository.updateProfileImage(profileImage)
    }
}
