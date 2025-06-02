package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import javax.inject.Inject

class GetMyProfileUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getUserFlow()
}
