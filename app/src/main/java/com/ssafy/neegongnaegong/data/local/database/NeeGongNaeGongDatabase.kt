package com.ssafy.neegongnaegong.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.neegongnaegong.data.local.database.dao.NotificationDao
import com.ssafy.neegongnaegong.data.local.database.dao.NotificationRemoteKeyDao
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationRemoteKeyEntity

@Database(
    entities = [
        NotificationEntity::class,
        NotificationRemoteKeyEntity::class
    ],
    version = 1
)
abstract class NeeGongNaeGongDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
    abstract fun notificationRemoteKeyDao(): NotificationRemoteKeyDao
}
