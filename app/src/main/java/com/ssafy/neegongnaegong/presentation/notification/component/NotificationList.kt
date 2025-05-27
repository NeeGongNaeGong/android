package com.ssafy.neegongnaegong.presentation.notification.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiModel

@Composable
fun NotificationList(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    notificationList: LazyPagingItems<NotificationUiModel>,
    onDeleteNotification: (NotificationUiModel) -> Unit,
    onMoveNotification: (NotificationUiModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(notificationList.itemCount) { index: Int ->
            val data: NotificationUiModel = notificationList[index] ?: return@items
            Notification(
                modifier = Modifier.fillMaxWidth(),
                image = data.image,
                user = data.user,
                content = data.content,
                isRead = data.isRead,
                onDelete = { onDeleteNotification(data) },
                onMove = { onMoveNotification(data) }
            )

            if (index < notificationList.itemCount - 1) {
                HorizontalDivider()
            }
        }
    }
}
