package com.ssafy.neegongnaegong.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalNotificationDataSource {
    @Query("SELECT * FROM notifications ORDER BY createdAt DESC")
    fun getAllNotifications(): PagingSource<Int, NotificationEntity>

    @Query("SELECT * FROM notifications WHERE id = :id")
    fun getNotificationById(id: Long): Flow<NotificationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<NotificationEntity>)

    @Query("DELETE FROM notifications WHERE id = :notificationId")
    suspend fun deleteById(notificationId: Long)

    @Update
    suspend fun update(notification: NotificationEntity)

    @Query("UPDATE notifications SET read = :isRead WHERE id = :id")
    suspend fun updateReadById(
        id: Long,
        isRead: Boolean,
    )

    @Query("DELETE FROM notifications")
    suspend fun clearAll()
}
