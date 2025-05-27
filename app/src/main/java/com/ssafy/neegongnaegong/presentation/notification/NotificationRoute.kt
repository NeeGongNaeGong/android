package com.ssafy.neegongnaegong.presentation.notification

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.presentation.base.CollectSideEffects
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiModel
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager

@Composable
fun NotificationRoute() {
    val viewModel: NotificationViewModel = hiltViewModel()
    val listState: LazyListState = rememberLazyListState()
    val uiState: NotificationContract.State by viewModel.uiState.collectAsStateWithLifecycle()
    val notificationList: LazyPagingItems<NotificationUiModel> = viewModel
        .notificationList
        .collectAsLazyPagingItems()

    CollectSideEffects(effectFlow = viewModel.effect) { effect: NotificationContract.Effect ->
        when (effect) {
            is NotificationContract.Effect.ShowErrorMessage -> {
                SnackbarManager.showErrorMessage(message = effect.message)
            }

            is NotificationContract.Effect.ScrollToFirstPosition -> {
                listState.animateScrollToItem(0)
            }
        }
    }

    NotificationScreen(
        listState = listState,
        isRefresh = uiState.isLoading,
        notificationList = notificationList,
        onRefresh = {
            val event = NotificationContract.Event.RefreshEvent
            viewModel.setEvent(event = event)
        },
        onDeleteAll = {
            val event = NotificationContract.Event.DeleteAllNotification
            viewModel.setEvent(event = event)
        },
        onDeleteNotification = { notificationUiModel: NotificationUiModel ->
            val event = NotificationContract.Event.DeleteNotification(data = notificationUiModel)
            viewModel.setEvent(event = event)
        },
        onMoveNotification = { notificationUiModel: NotificationUiModel ->
            val event = NotificationContract.Event.MoveNotification(data = notificationUiModel)
            viewModel.setEvent(event = event)
        }
    )
}
