package com.ssafy.neegongnaegong.domain.usecase.notification

import com.ssafy.neegongnaegong.domain.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationUseCase
    @Inject
    constructor(private val notificationRepository: NotificationRepository) {
        operator fun invoke(notificationId: Long) =
            notificationRepository.deleteNotification(
                notificationId = notificationId,
            )
    }
