package com.ssafy.neegongnaegong.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_remote_keys")
data class NotificationRemoteKeyEntity(
    @PrimaryKey
    val id: Long,
    val nextCursor: Long?,
)
