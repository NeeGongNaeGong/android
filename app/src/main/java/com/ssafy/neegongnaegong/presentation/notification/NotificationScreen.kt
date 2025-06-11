package com.ssafy.neegongnaegong.presentation.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.model.notification.NotificationType
import com.ssafy.neegongnaegong.presentation.component.refresh.DefaultRefreshBox
import com.ssafy.neegongnaegong.presentation.notification.component.NotificationList
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    isRefresh: Boolean,
    listState: LazyListState,
    notificationList: LazyPagingItems<NotificationUiModel>,
    onNavigateUp: () -> Unit,
    onRefresh: () -> Unit,
    onDeleteAll: () -> Unit,
    onDeleteNotification: (NotificationUiModel) -> Unit,
    onMoveNotification: (NotificationUiModel) -> Unit,
    onAcceptGroupJoinRequest: (NotificationUiModel) -> Unit,
    onRejectGroupJoinRequest: (NotificationUiModel) -> Unit,
) {
    DefaultRefreshBox(
        isRefreshing = isRefresh,
        onRefresh = onRefresh,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier =
                        Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .size(22.dp)
                            .clickable(onClick = onNavigateUp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = NeeGongNaeGongTheme.colorScheme.primaryText,
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.notification),
                    style = NeeGongNaeGongTheme.typography.bodyMedium,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.delete_all),
                    modifier =
                        Modifier
                            .align(Alignment.CenterEnd)
                            .clickable(
                                indication = null,
                                interactionSource = null,
                                onClick = onDeleteAll,
                            ),
                    textAlign = TextAlign.End,
                    style = NeeGongNaeGongTheme.typography.bodySmall,
                    color = NeeGongNaeGongTheme.colorScheme.primaryText,
                )
            }

            NotificationList(
                modifier = Modifier.fillMaxSize(),
                listState = listState,
                notificationList = notificationList,
                onDeleteNotification = onDeleteNotification,
                onMoveNotification = onMoveNotification,
                onAcceptGroupJoinRequest = onAcceptGroupJoinRequest,
                onRejectGroupJoinRequest = onRejectGroupJoinRequest,
            )
        }
    }
}

@Composable
@Preview
fun NotificationPreviewScreen() {
    // image = "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTRI8A6M23RTePWn8of5fgwRzSMMzRy_6mZP7OrP79VF3ByzCoRcyfx6bYr9w4bH9zdVfpV_LP9hBAudM5SRGyjnbbEhnrs2vWZKF8wySI",
    val testModel =
        NotificationUiModel(
            id = Random.nextLong(),
            image = "",
            user = "홍길동",
            content = "님이 ㅋㅋㅋ 게시글에 답글을 추가했습니다.",
            isRead = true,
            type = NotificationType.SYSTEM,
            senderId = 0,
            studyGroupId = null,
            studyGroupName = null,
        )
    val testList = List<NotificationUiModel>(500) { testModel }
    val fakeFlow = flowOf(PagingData.from(testList)).collectAsLazyPagingItems()

    NotificationScreen(
        isRefresh = false,
        listState = rememberLazyListState(),
        notificationList = fakeFlow,
        onNavigateUp = {},
        onRefresh = {},
        onDeleteAll = {},
        onDeleteNotification = {},
        onMoveNotification = {},
        onAcceptGroupJoinRequest = {},
        onRejectGroupJoinRequest = {},
    )
}
