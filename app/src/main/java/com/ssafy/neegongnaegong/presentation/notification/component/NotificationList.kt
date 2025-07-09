package com.ssafy.neegongnaegong.presentation.notification.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.domain.model.notification.NotificationType
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random

@Composable
fun NotificationList(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    notificationList: LazyPagingItems<NotificationUiModel>,
    onDeleteNotification: (NotificationUiModel) -> Unit,
    onMoveNotification: (NotificationUiModel) -> Unit,
    onAcceptGroupJoinRequest: (NotificationUiModel) -> Unit,
    onRejectGroupJoinRequest: (NotificationUiModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
    ) {
        items(
            count = notificationList.itemCount,
            key = { index: Int -> notificationList[index]?.id ?: index },
        ) { index: Int ->
            val data: NotificationUiModel = notificationList[index] ?: return@items
            Notification(
                modifier = Modifier.fillMaxWidth(),
                image = data.image,
                user = data.user,
                content = data.content,
                isRead = data.isRead,
                isGroupJoinRequest = data.type == NotificationType.GROUP_JOIN_REQUEST,
                onDelete = { onDeleteNotification(data) },
                onMove = { onMoveNotification(data) },
                onAccept = { onAcceptGroupJoinRequest(data) },
                onReject = { onRejectGroupJoinRequest(data) },
            )
        }
    }
}

@Composable
@NeeGongNaeGongPreviews
fun PreviewNotificationList() {
    val testModel = {
        NotificationUiModel(
            id = Random.nextLong(),
            image =
                "https://encrypted-tbn1.gstatic.com/" +
                    "images?q=tbn:ANd9GcTRI8A6M23RTePWn8of5fgwRzSMMzRy" +
                    "_6mZP7OrP79VF3ByzCoRcyfx6bYr9w4bH9zdVfpV" +
                    "_LP9hBAudM5SRGyjnbbEhnrs2vWZKF8wySI",
            user = "홍길동",
            content = "님이 ㅋㅋㅋ 게시글에 답글을 추가했습니다.",
            isRead = true,
            type = NotificationType.SYSTEM,
            senderId = 0,
            studyGroupId = null,
            studyGroupName = null,
        )
    }
    val testList = List<NotificationUiModel>(500) { testModel() }
    val fakeFlow = MutableStateFlow(PagingData.from(testList)).collectAsLazyPagingItems()

    NeeGongNaeGongTheme {
        NotificationList(
            listState = rememberLazyListState(),
            notificationList = fakeFlow,
            onDeleteNotification = {},
            onMoveNotification = {},
            onAcceptGroupJoinRequest = {},
            onRejectGroupJoinRequest = {},
        )
    }
}
