package com.ssafy.neegongnaegong.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationRemoteKeyEntity

@Dao
interface LocalNotificationRemoteKeyDataSource {
    @Query("SELECT * FROM notification_remote_keys WHERE id = :id")
    suspend fun getRemoteKey(id: Long): NotificationRemoteKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<NotificationRemoteKeyEntity>)

    @Query("DELETE FROM notification_remote_keys")
    suspend fun clearAll()
}
