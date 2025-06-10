package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckShowProfileImageWarningUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Boolean> = flow {
        emit(true)
    }
}
