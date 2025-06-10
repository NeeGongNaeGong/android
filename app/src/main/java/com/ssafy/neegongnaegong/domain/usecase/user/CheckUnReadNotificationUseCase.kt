package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckUnReadNotificationUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Boolean> = userRepository.findUnReadNotification()
}
