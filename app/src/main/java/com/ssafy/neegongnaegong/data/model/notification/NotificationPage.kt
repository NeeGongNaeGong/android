package com.ssafy.neegongnaegong.data.model.notification

data class NotificationPage(
    val content: List<GetNotificationResponse>,
    val hasNext: Boolean,
    val cursorId: Long?,
)
