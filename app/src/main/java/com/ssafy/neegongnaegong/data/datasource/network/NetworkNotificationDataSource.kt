package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.notification.NotificationPage
import kotlinx.coroutines.flow.Flow

interface NetworkNotificationDataSource {

    fun getNotifications(cursorId: Long?, size: Int): Flow<NotificationPage>

    fun deleteNotification(notificationId: Long): Flow<Unit>

    fun deleteAllNotifications(): Flow<Unit>
}
