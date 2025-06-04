package com.ssafy.neegongnaegong.domain.repository

import androidx.paging.PagingData
import com.ssafy.neegongnaegong.domain.model.notification.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun getNotifications(): Flow<PagingData<Notification>>

    fun deleteNotification(notificationId: Long): Flow<Unit>

    fun deleteAllNotifications(): Flow<Unit>
}
