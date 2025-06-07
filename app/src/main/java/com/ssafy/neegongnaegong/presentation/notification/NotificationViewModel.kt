package com.ssafy.neegongnaegong.presentation.notification

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.exception.InvalidGroupIdException
import com.ssafy.neegongnaegong.domain.model.notification.Notification
import com.ssafy.neegongnaegong.domain.model.notification.NotificationType
import com.ssafy.neegongnaegong.domain.usecase.notification.DeleteAllNotificationsUseCase
import com.ssafy.neegongnaegong.domain.usecase.notification.DeleteNotificationUseCase
import com.ssafy.neegongnaegong.domain.usecase.notification.GetNotificationUseCase
import com.ssafy.neegongnaegong.domain.usecase.notification.ReadNotificationUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.ApproveStudyGroupJoinUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.RejectStudyGroupJoinUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiMapper.toUiModel
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationUseCase: GetNotificationUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase,
    private val deleteAllNotificationUseCase: DeleteAllNotificationsUseCase,
    private val readNotificationUseCase: ReadNotificationUseCase,
    private val approveStudyGroupJoinUseCase: ApproveStudyGroupJoinUseCase,
    private val rejectStudyGroupJoinUseCase: RejectStudyGroupJoinUseCase
) : BaseViewModel<NotificationContract.Event, NotificationContract.State, NotificationContract.Effect>() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val notificationList: StateFlow<PagingData<NotificationUiModel>> by lazy {
        uiState.distinctUntilChangedBy { uiState: NotificationContract.State ->
            uiState.isLoading
        }.filter { uiState: NotificationContract.State ->
            uiState.isLoading
        }.safeFlatMapLatest(errorContext = NotificationContract.Error.ShowErrorMessage) {
            getNotificationUseCase()
        }.map { pagingData: PagingData<Notification> ->
            pagingData.toUiModel()
        }.onEach {
            endLoad()
        }.cachedIn(viewModelScope)
            .toViewModelState(PagingData.empty())
    }

    override fun createInitialState(): NotificationContract.State = NotificationContract.State()

    override fun handleEvent(event: NotificationContract.Event) = when (event) {
        NotificationContract.Event.RefreshEvent -> handleRefresh()
        NotificationContract.Event.DeleteAllNotification -> handleDeleteAllNotification()
        is NotificationContract.Event.DeleteNotification -> handleDeleteNotification(data = event.data)
        is NotificationContract.Event.MoveNotification -> handleMoveNotification(data = event.data)
        is NotificationContract.Event.AcceptGroupJoinRequest -> handleAcceptGroupJoin(data = event.data)
        is NotificationContract.Event.RejectGroupJoinRequest -> handleRejectGroupJoin(data = event.data)
    }

    private fun endLoad() {
        setState { copy(isLoading = false) }
    }

    private fun handleRefresh() {
        setState { copy(isLoading = true) }

        val sideEffect = NotificationContract.Effect.ScrollToFirstPosition
        setEffect { sideEffect }
    }

    private fun handleDeleteAllNotification() {
        viewModelScope.safeLaunch(errorContext = NotificationContract.Error.ShowErrorMessage) {
            deleteAllNotificationUseCase().withLoading { isModifying ->
                setState { copy(isModifying = isModifying) }
            }.firstOrNull()
        }
    }

    private fun handleDeleteNotification(data: NotificationUiModel) {
        viewModelScope.safeLaunch(errorContext = NotificationContract.Error.ShowErrorMessage) {
            deleteNotificationUseCase(notificationId = data.id).withLoading { isModifying ->
                setState { copy(isModifying = isModifying) }
            }.firstOrNull()
        }
    }

    private fun handleMoveNotification(data: NotificationUiModel) {
        viewModelScope.safeLaunch(errorContext = NotificationContract.Error.ShowErrorMessage) {
            handleNotification(data = data)
        }
    }

    private fun handleAcceptGroupJoin(data: NotificationUiModel) {
        viewModelScope.safeLaunch(errorContext = NotificationContract.Error.ShowErrorMessage) {
            approveStudyGroupJoinUseCase(
                studyGroupId = data.studyGroupId,
                userId = data.senderId,
                notificationId = data.id
            ).firstOrNull()
        }
    }

    private fun handleRejectGroupJoin(data: NotificationUiModel) {
        viewModelScope.safeLaunch(errorContext = NotificationContract.Error.ShowErrorMessage) {
            rejectStudyGroupJoinUseCase(
                studyGroupId = data.studyGroupId,
                userId = data.senderId,
                notificationId = data.id
            ).firstOrNull()
        }
    }

    /**
     * 1. 그룹 가입 요청을 제외한 모든 내용은 "읽음 처리"를 수행합니다.
     * 2. 화면 이동이 필요한 타입은 다음과 같습니다:
     * - [NotificationType.GROUP_JOIN_APPROVE]
     * - [NotificationType.NOTICE_POSTED]
     * - [NotificationType.VOTE_CREATED]
     * - [NotificationType.VOTE_ENDED]
     */
    private suspend fun handleNotification(data: NotificationUiModel) {
        if (data.type != NotificationType.GROUP_JOIN_REQUEST && !data.isRead) {
            readNotificationUseCase(notificationId = data.id).firstOrNull()
        }

        when (data.type) {
            NotificationType.GROUP_JOIN_APPROVE -> {
                val effect = NotificationContract.Effect.NavigateToGroup(groupId = data.senderId)
                setEffect { effect }
            }

            NotificationType.NOTICE_POSTED -> {
                val effect = NotificationContract.Effect.NavigateToNotice(noticeId = data.senderId)
                setEffect { effect }
            }

            NotificationType.VOTE_CREATED -> {
                val effect = NotificationContract.Effect.NavigateToVote(voteId = data.senderId)
                setEffect { effect }
            }

            NotificationType.VOTE_ENDED -> {
                val effect = NotificationContract.Effect.NavigateToVote(voteId = data.senderId)
                setEffect { effect }
            }

            else -> Unit
        }
    }

    override fun handleException(
        e: Throwable,
        errorContext: ErrorContext,
        retry: () -> Unit
    ) {
        endLoad()

        val sideEffect: NotificationContract.Effect = when (e) {
            is InvalidGroupIdException -> {
                NotificationContract.Effect.ShowInvalidGroupIdErrorMessage
            }

            else -> {
                val message: String = e.message ?: return
                NotificationContract.Effect.ShowErrorMessage(message)
            }
        }
        setEffect { sideEffect }
    }
}
