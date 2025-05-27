package com.ssafy.neegongnaegong.presentation.notification.data

import androidx.compose.runtime.Stable

@Stable
data class NotificationUiModel(
    // TODO (도메인 레이어와 연결 시에 수정될 수 있습니다.)
    val id: Long,
    val image: String,
    val user: String,
    val content: String,
    val isRead: Boolean
)
