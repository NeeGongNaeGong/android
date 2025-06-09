package com.ssafy.neegongnaegong.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.neegongnaegong.data.datasource.network.NetworkNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.dao.LocalNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.data.NotificationMapper.toEntity
import com.ssafy.neegongnaegong.data.local.database.data.NotificationMapper.toNotification
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity
import com.ssafy.neegongnaegong.data.model.notification.GetNotificationResponse
import com.ssafy.neegongnaegong.data.paging.NotificationRemoteMediator
import com.ssafy.neegongnaegong.domain.model.notification.Notification
import com.ssafy.neegongnaegong.domain.repository.NotificationRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val networkNotificationDataSource: NetworkNotificationDataSource,
    private val localNotificationDataSource: LocalNotificationDataSource,
    private val notificationRemoteMediator: NotificationRemoteMediator,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : NotificationRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getNotifications() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        remoteMediator = notificationRemoteMediator,
        pagingSourceFactory = { localNotificationDataSource.getAllNotifications() }
    ).flow.map { pagingData: PagingData<NotificationEntity> ->
        pagingData.toNotification()
    }.flowOn(context = ioDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getNotification(
        notificationId: Long
    ): Flow<Notification> = networkNotificationDataSource
        .getNotification(notificationId = notificationId)
        .onEach { getNotificationResponse: GetNotificationResponse ->
            val entity: NotificationEntity = getNotificationResponse.toEntity()
            localNotificationDataSource.update(notification = entity)
        }.flatMapLatest {
            localNotificationDataSource.getNotificationById(id = notificationId)
        }.filterNotNull().map { entity: NotificationEntity ->
            entity.toNotification()
        }

    override fun deleteNotification(
        notificationId: Long
    ): Flow<Unit> = networkNotificationDataSource
        .deleteNotification(notificationId = notificationId)
        .onEach { localNotificationDataSource.deleteById(notificationId = notificationId) }
        .flowOn(context = ioDispatcher)

    override fun deleteAllNotifications(): Flow<Unit> = networkNotificationDataSource
        .deleteAllNotifications()
        .onEach { localNotificationDataSource.clearAll() }
        .flowOn(context = ioDispatcher)

    override fun readNotification(notificationId: Long): Flow<Unit> = networkNotificationDataSource
        .readNotification(notificationId = notificationId)
        .onEach { localNotificationDataSource.updateReadById(id = notificationId, isRead = true) }
        .flowOn(context = ioDispatcher)

    companion object {
        const val PAGE_SIZE = 30
    }
}
