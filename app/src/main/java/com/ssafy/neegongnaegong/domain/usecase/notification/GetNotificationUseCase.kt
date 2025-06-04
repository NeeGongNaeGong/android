package com.ssafy.neegongnaegong.domain.usecase.notification

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.notification.Notification
import com.ssafy.neegongnaegong.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(private val notificationRepository: NotificationRepository) {
    operator fun invoke(): Flow<PagingData<Notification>> =
        notificationRepository.getNotifications()
}
