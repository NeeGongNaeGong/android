package com.ssafy.neegongnaegong.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssafy.neegongnaegong.data.local.database.dao.LocalNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.dao.LocalNotificationRemoteKeyDataSource
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationRemoteKeyEntity

@Database(
    entities = [
        NotificationEntity::class,
        NotificationRemoteKeyEntity::class
    ],
    version = 2
)
abstract class NeeGongNaeGongDatabase : RoomDatabase() {
    abstract fun localNotificationDataSource(): LocalNotificationDataSource
    abstract fun localNotificationRemoteKeyDataSource(): LocalNotificationRemoteKeyDataSource
}
