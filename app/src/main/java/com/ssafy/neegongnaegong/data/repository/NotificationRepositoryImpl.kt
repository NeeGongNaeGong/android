package com.ssafy.neegongnaegong.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ssafy.neegongnaegong.data.datasource.network.NetworkNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.dao.NotificationDao
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
    private val notificationDataSource: NetworkNotificationDataSource,
    private val notificationRemoteMediator: NotificationRemoteMediator,
    private val notificationDao: NotificationDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : NotificationRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getNotifications() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        remoteMediator = notificationRemoteMediator,
        pagingSourceFactory = { notificationDao.getAllNotifications() }
    ).flow.map { pagingData: PagingData<NotificationEntity> ->
        pagingData.toNotification()
    }.flowOn(context = ioDispatcher)

    override fun deleteNotification(notificationId: Long): Flow<Unit> = notificationDataSource
        .deleteNotification(notificationId = notificationId)
        .onEach { notificationDao.deleteById(notificationId = notificationId) }
        .flowOn(context = ioDispatcher)

    override fun deleteAllNotifications(): Flow<Unit> = notificationDataSource
        .deleteAllNotifications()
        .onEach { notificationDao.clearAll() }
        .flowOn(context = ioDispatcher)

    companion object {
        const val PAGE_SIZE = 30
    }
}
