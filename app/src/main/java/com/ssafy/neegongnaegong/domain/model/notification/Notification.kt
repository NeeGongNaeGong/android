package com.ssafy.neegongnaegong.domain.model.notification

import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val cursorId: Long,
    val receiverId: Long,
    val senderId: Long,
    val notificationType: NotificationType,
    val senderType: String,
    val displayName: String,
    val profileImg: String,
    val content: String,
    val createdAt: LocalDateTime,
    val read: Boolean,
)
