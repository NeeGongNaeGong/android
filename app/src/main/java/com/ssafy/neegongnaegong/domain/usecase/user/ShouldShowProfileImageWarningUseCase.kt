package com.ssafy.neegongnaegong.domain.usecase.user

import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

class ShouldShowProfileImageWarningUseCase
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) {
        private val thresholdMillis = 7.days.toLong(unit = DurationUnit.MILLISECONDS)

        operator fun invoke(): Flow<Boolean> =
            userRepository.getProfileImageWarningAcceptedAt()
                .map { acceptedTime: Long ->
                    val now = System.currentTimeMillis()
                    (now - acceptedTime) > thresholdMillis
                }
    }
