package com.ssafy.neegongnaegong.domain.usecase.notification

import com.ssafy.neegongnaegong.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadNotificationUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    operator fun invoke(notificationId: Long): Flow<Unit> =
        notificationRepository.readNotification(notificationId = notificationId)
}
