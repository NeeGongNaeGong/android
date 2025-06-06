package com.ssafy.neegongnaegong.data.local.database.data

import androidx.paging.PagingData
import androidx.paging.map
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationEntity
import com.ssafy.neegongnaegong.data.local.database.entity.NotificationRemoteKeyEntity
import com.ssafy.neegongnaegong.data.model.notification.GetNotificationResponse
import com.ssafy.neegongnaegong.data.model.notification.NotificationRemoteType
import com.ssafy.neegongnaegong.domain.model.notification.Notification
import com.ssafy.neegongnaegong.domain.model.notification.NotificationType
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

internal object NotificationMapper {

    fun GetNotificationResponse.toKey(cursorId: Long?) = NotificationRemoteKeyEntity(
        id = id,
        nextCursor = cursorId,
    )

    fun List<GetNotificationResponse>.toKey(cursorId: Long?): List<NotificationRemoteKeyEntity> =
        map { it.toKey(cursorId = cursorId) }


    fun GetNotificationResponse.toEntity() = NotificationEntity(
        id = id,
        cursorId = cursorId,
        receiverId = receiverId,
        senderId = senderId,
        notificationType = notificationType.toNotificationType(),
        senderType = senderType,
        displayName = displayName,
        profileImg = profileImg,
        content = content,
        createdAt = createdAt.toEpochMilli(),
        read = read
    )

    fun List<GetNotificationResponse>.toEntity(): List<NotificationEntity> = map { it.toEntity() }

    fun NotificationEntity.toNotification() = Notification(
        id = id,
        cursorId = cursorId,
        receiverId = receiverId,
        senderId = senderId,
        notificationType = notificationType.toNotificationType(),
        senderType = senderType,
        displayName = displayName,
        profileImg = profileImg,
        content = content,
        createdAt = createdAt.toLocalDateTime(),
        read = read
    )

    fun PagingData<NotificationEntity>.toNotification(): PagingData<Notification> = map {
        it.toNotification()
    }

    fun NotificationRemoteType.toNotificationType() = NotificationLocalType.valueOf(name)

    fun LocalDateTime.toEpochMilli(): Long {
        val zoneId = ZoneId.systemDefault()
        val instant = this.atZone(zoneId).toInstant()
        return instant.toEpochMilli()
    }

    fun Long.toLocalDateTime(): LocalDateTime {
        val zoneId = ZoneId.systemDefault()
        return Instant.ofEpochMilli(this).atZone(zoneId).toLocalDateTime()
    }

    fun NotificationLocalType.toNotificationType() = NotificationType.valueOf(name)
}
