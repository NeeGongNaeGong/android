package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.notification.GetNotificationResponse
import com.ssafy.neegongnaegong.data.model.notification.NotificationPage
import com.ssafy.neegongnaegong.data.remote.NotificationApi
import com.ssafy.neegongnaegong.domain.exception.ApiException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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

    override fun getNotification(notificationId: Long): Flow<GetNotificationResponse> = apiFlow {
        notificationApi.getNotification(notificationId = notificationId)
    }

    override fun deleteNotification(notificationId: Long): Flow<Unit> = apiFlow {
        notificationApi.deleteNotification(notificationId = notificationId)
    }

    override fun deleteAllNotifications(): Flow<Unit> = apiFlow {
        notificationApi.deleteAllNotifications()
    }

    override fun readNotification(notificationId: Long): Flow<Unit> = apiFlow {
        notificationApi.readNotification(notificationId = notificationId)
    }.catch { throwable: Throwable ->
        if (throwable is ApiException.ConflictException) emit(value = Unit)
        else throw throwable
    }
}
