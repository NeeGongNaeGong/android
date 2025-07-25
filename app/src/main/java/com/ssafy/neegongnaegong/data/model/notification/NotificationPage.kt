package com.ssafy.neegongnaegong.data.model.notification

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

data class NotificationPage(
    val content: List<GetNotificationResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData,
)
