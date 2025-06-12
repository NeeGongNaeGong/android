package com.ssafy.neegongnaegong.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ssafy.neegongnaegong.data.local.database.data.NotificationLocalType

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey
    val id: Long,
    val cursorId: Long,
    val receiverId: Long,
    val senderId: Long,
    val notificationType: NotificationLocalType,
    val senderType: String,
    val displayName: String,
    val profileImg: String,
    val content: String,
    val createdAt: Long,
    val read: Boolean,
    val studyGroupId: Long?,
    val studyGroupName: String?,
)
