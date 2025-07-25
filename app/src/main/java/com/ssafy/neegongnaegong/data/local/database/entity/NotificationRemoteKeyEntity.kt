package com.ssafy.neegongnaegong.data.local.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

@Entity(tableName = "notification_remote_keys")
data class NotificationRemoteKeyEntity(
    @PrimaryKey
    val id: Long,
    @Embedded
    val nextCursor: NextCursorData?,
)
