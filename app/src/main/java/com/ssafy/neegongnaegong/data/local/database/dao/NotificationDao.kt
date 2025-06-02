package com.ssafy.neegongnaegong.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY createdAt DESC")
    fun getAllNotifications(): PagingSource<Int, NotificationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<NotificationEntity>)

    @Query("DELETE FROM notifications WHERE id = :notificationId")
    suspend fun deleteById(notificationId: Long)

    @Query("DELETE FROM notifications")
    suspend fun clearAll()
}
