package com.ssafy.neegongnaegong.data.model.notification

import java.time.LocalDateTime

data class GetNotificationResponse(
    val id: Long,
    val cursorId: Long,
    val receiverId: Long,
    val senderId: Long,
    val notificationType: NotificationRemoteType,
    val senderType: String,
    val displayName: String,
    val profileImg: String,
    val content: String,
    val createdAt: LocalDateTime,
    val read: Boolean,
    val studyGroupId: Long?,
    val studyGroupName: String?,
)
