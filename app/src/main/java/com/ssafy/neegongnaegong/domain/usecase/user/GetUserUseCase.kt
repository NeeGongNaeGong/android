package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: Long): Flow<User> {
        return userRepository.getUser(id)
    }
}
