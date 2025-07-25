package com.ssafy.neegongnaegong.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ssafy.neegongnaegong.data.local.database.converter.LocalDateTimeConverter
import com.ssafy.neegongnaegong.data.local.database.dao.LocalNotificationDataSource
import com.ssafy.neegongnaegong.data.local.database.dao.LocalNotificationRemoteKeyDataSource
import com.ssafy.neegongnaegong.data.local.database.dao.StudyGroupVoteHistoryDao
import com.ssafy.neegongnaegong.data.local.database.dao.StudyGroupVoteHistoryRemoteKeyDao
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationRemoteKeyEntity
import com.ssafy.neegongnaegong.data.local.database.entity.StudyGroupVoteHistory
import com.ssafy.neegongnaegong.data.local.database.entity.StudyGroupVoteHistoryRemoteKey

@Database(
    entities = [
        NotificationEntity::class,
        NotificationRemoteKeyEntity::class,
        StudyGroupVoteHistory::class,
        StudyGroupVoteHistoryRemoteKey::class,
    ],
    version = 5,
    exportSchema = true,
    autoMigrations = [
        // 투표 목록 저장하는 Table 생성
        AutoMigration(from = 2, to = 3),
    ],
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class NeeGongNaeGongDatabase : RoomDatabase() {
    abstract fun localNotificationDataSource(): LocalNotificationDataSource

    abstract fun localNotificationRemoteKeyDataSource(): LocalNotificationRemoteKeyDataSource

    abstract fun studyGroupVoteHistoryDao(): StudyGroupVoteHistoryDao

    abstract fun studyGroupVoteHistoryRemoteKeyDao(): StudyGroupVoteHistoryRemoteKeyDao
}
