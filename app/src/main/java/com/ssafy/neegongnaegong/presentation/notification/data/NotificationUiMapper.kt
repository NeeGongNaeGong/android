package com.ssafy.neegongnaegong.presentation.notification.data

import androidx.paging.PagingData
import androidx.paging.map
import com.ssafy.neegongnaegong.domain.model.notification.Notification

internal object NotificationUiMapper {
    fun Notification.toUiModel() =
        NotificationUiModel(
            id = id,
            image = profileImg,
            user = displayName,
            content = content,
            isRead = read,
            type = notificationType,
            senderId = senderId,
            studyGroupId = studyGroupId,
            studyGroupName = studyGroupName,
        )

    fun PagingData<Notification>.toUiModel(): PagingData<NotificationUiModel> =
        map {
            it.toUiModel()
        }
}
