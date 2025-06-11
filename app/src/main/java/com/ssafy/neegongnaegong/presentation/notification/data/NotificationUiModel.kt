package com.ssafy.neegongnaegong.presentation.notification.data

import androidx.compose.runtime.Stable
import com.ssafy.neegongnaegong.domain.model.notification.NotificationType

@Stable
data class NotificationUiModel(
    val id: Long,
    val image: String,
    val user: String,
    val content: String,
    val isRead: Boolean,
    val type: NotificationType,
    val senderId: Long,
    val studyGroupId: Long?,
    val studyGroupName: String?,
)
