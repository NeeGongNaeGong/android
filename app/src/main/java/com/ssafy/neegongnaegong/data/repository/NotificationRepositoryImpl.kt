package com.ssafy.neegongnaegong.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.neegongnaegong.data.datasource.network.NetworkNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.dao.LocalNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.data.NotificationMapper.toNotification
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity
import com.ssafy.neegongnaegong.data.paging.NotificationRemoteMediator
import com.ssafy.neegongnaegong.domain.repository.NotificationRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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

    override fun deleteNotification(notificationId: Long): Flow<Unit> = networkNotificationDataSource
        .deleteNotification(notificationId = notificationId)
        .onEach { localNotificationDataSource.deleteById(notificationId = notificationId) }
        .flowOn(context = ioDispatcher)

    override fun deleteAllNotifications(): Flow<Unit> = networkNotificationDataSource
        .deleteAllNotifications()
        .onEach { localNotificationDataSource.clearAll() }
        .flowOn(context = ioDispatcher)

    companion object {
        const val PAGE_SIZE = 30
    }
}
