package com.ssafy.neegongnaegong.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.neegongnaegong.data.local.database.data.NotificationType

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    val id: Long,
    val cursorId: Long,
    val receiverId: Long,
    val senderId: Long,
    val notificationType: NotificationType,
    val senderType: String,
    val displayName: String,
    val profileImg: String,
    val content: String,
    val createdAt: Long,
    val read: Boolean
)
