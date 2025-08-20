package com.ssafy.neegongnaegong.presentation.notification

import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.base.CollectSideEffects
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiModel
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager

@Composable
fun NotificationRoute(
    viewModel: NotificationViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToGroup: (groupId: Long) -> Unit,
    navigateToNotice: (groupId: Long, noticeId: Long) -> Unit,
    navigateToVote: (groupId: Long, voteId: Long) -> Unit,
) {
    val context: Context = LocalContext.current
    val listState: LazyListState = rememberLazyListState()
    val uiState: NotificationContract.State by viewModel.uiState.collectAsStateWithLifecycle()
    val notificationList: LazyPagingItems<NotificationUiModel> =
        viewModel
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

            NotificationContract.Effect.NavigateUp -> {
                navigateUp()
            }

            is NotificationContract.Effect.NavigateToGroup -> {
                navigateToGroup(effect.groupId)
            }

            is NotificationContract.Effect.NavigateToNotice -> {
                navigateToNotice(effect.groupId, effect.noticeId)
            }

            is NotificationContract.Effect.NavigateToVote -> {
                navigateToVote(effect.groupId, effect.voteId)
            }

            NotificationContract.Effect.ShowInvalidGroupIdErrorMessage -> {
                val message: String = context.getString(R.string.invalid_group_id)
                SnackbarManager.showErrorMessage(message = message)
            }
        }
    }

    NotificationScreen(
        listState = listState,
        isRefresh = uiState.isLoading,
        notificationList = notificationList,
        onNavigateUp = {
            val event = NotificationContract.Event.OnNavigateUp
            viewModel.setEvent(event = event)
        },
        onRefresh = {
            val event = NotificationContract.Event.RefreshEvent
            viewModel.setEvent(event = event)
        },
        onDeleteAll = {
            val event = NotificationContract.Event.DeleteAllNotification
            viewModel.setEvent(event = event)
        },
        onDeleteNotification = { uiModel: NotificationUiModel ->
            val event = NotificationContract.Event.DeleteNotification(data = uiModel)
            viewModel.setEvent(event = event)
        },
        onMoveNotification = { uiModel: NotificationUiModel ->
            val event = NotificationContract.Event.MoveNotification(data = uiModel)
            viewModel.setEvent(event = event)
        },
        onAcceptGroupJoinRequest = { uiModel: NotificationUiModel ->
            val event = NotificationContract.Event.AcceptGroupJoinRequest(data = uiModel)
            viewModel.setEvent(event = event)
        },
        onRejectGroupJoinRequest = { uiModel: NotificationUiModel ->
            val event = NotificationContract.Event.RejectGroupJoinRequest(data = uiModel)
            viewModel.setEvent(event = event)
        },
    )

    if (uiState.isModifying || uiState.isLoading) LoadingDialog()
}
