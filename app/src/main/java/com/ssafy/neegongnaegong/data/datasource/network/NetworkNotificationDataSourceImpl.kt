package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.notification.NotificationPage
import com.ssafy.neegongnaegong.data.remote.NotificationApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkNotificationDataSourceImpl @Inject constructor(
    private val notificationApi: NotificationApi
) : NetworkNotificationDataSource {

    override fun getNotifications(
        cursorId: Long?,
        size: Int
    ): Flow<NotificationPage> = apiFlow {
        notificationApi.getNotifications(cursorId = cursorId, size = size)
    }

    override fun deleteNotification(notificationId: Long): Flow<Unit> = apiFlow {
        notificationApi.deleteNotification(notificationId = notificationId)
    }

    override fun deleteAllNotifications(): Flow<Unit> = apiFlow {
        notificationApi.deleteAllNotifications()
    }
}
