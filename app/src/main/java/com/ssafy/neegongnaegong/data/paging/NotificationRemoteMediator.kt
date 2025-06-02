package com.ssafy.neegongnaegong.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ssafy.neegongnaegong.data.datasource.network.NetworkNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.NeeGongNaeGongDatabase
import com.ssafy.neegongnaegong.data.local.database.dao.NotificationDao
import com.ssafy.neegongnaegong.data.local.database.dao.NotificationRemoteKeyDao
import com.ssafy.neegongnaegong.data.local.database.data.NotificationMapper.toEntity
import com.ssafy.neegongnaegong.data.local.database.data.NotificationMapper.toKey
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationRemoteKeyEntity
import com.ssafy.neegongnaegong.data.model.notification.NotificationPage
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NotificationRemoteMediator @Inject constructor(
    private val notificationDataSource: NetworkNotificationDataSource,
    private val database: NeeGongNaeGongDatabase
) : RemoteMediator<Int, NotificationEntity>() {

    private val notificationDao: NotificationDao = database.notificationDao()
    private val remoteKeyDao: NotificationRemoteKeyDao = database.notificationRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NotificationEntity>
    ): MediatorResult {
        val cursor: Long? = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKey: NotificationRemoteKeyEntity? = getRemoteKeyForLastItem(state = state)
                if (remoteKey == null) return MediatorResult.Success(endOfPaginationReached = false)

                val nextCursor: Long? = remoteKey.nextCursor
                if (nextCursor == null) return MediatorResult.Success(endOfPaginationReached = true)
                nextCursor
            }
        }

        return runCatching {
            val remotePager: NotificationPage = notificationDataSource.getNotifications(
                cursorId = cursor,
                size = state.config.pageSize
            ).first()
            val endOfPaginationReached: Boolean = !remotePager.hasNext

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    notificationDao.clearAll()
                }

                remoteKeyDao.insertAll(keys = remotePager.content.toKey(cursorId = remotePager.cursorId))
                notificationDao.insertAll(notifications = remotePager.content.toEntity())
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }.getOrElse { throwable: Throwable ->
            MediatorResult.Error(throwable = throwable)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, NotificationEntity>): NotificationRemoteKeyEntity? {
        return state.pages.lastOrNull { page: PagingSource.LoadResult.Page<Int, NotificationEntity> ->
            page.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { notification ->
            remoteKeyDao.getRemoteKey(notification.id)
        }
    }
}
