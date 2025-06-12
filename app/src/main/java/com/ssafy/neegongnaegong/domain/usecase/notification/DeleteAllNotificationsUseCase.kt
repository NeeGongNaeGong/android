package com.ssafy.neegongnaegong.domain.usecase.notification

import com.ssafy.neegongnaegong.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAllNotificationsUseCase
    @Inject
    constructor(private val notificationRepository: NotificationRepository) {
        operator fun invoke(): Flow<Unit> = notificationRepository.deleteAllNotifications()
    }
