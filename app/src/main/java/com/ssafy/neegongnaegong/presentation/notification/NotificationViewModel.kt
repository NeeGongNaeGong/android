package com.ssafy.neegongnaegong.presentation.notification

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.notification.Notification
import com.ssafy.neegongnaegong.domain.usecase.notification.DeleteAllNotificationsUseCase
import com.ssafy.neegongnaegong.domain.usecase.notification.DeleteNotificationUseCase
import com.ssafy.neegongnaegong.domain.usecase.notification.GetNotificationUseCase
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
            TODO("알람으로 이동합니다. ${data.id}")
            // TODO(알림 읽음 처리, 화면 전환, 수락,거절 로직이 추가될 예정입니다.)
        }
    }

    override fun handleException(
        e: Throwable,
        errorContext: ErrorContext,
        retry: () -> Unit
    ) {
        endLoad()

        val message: String = e.message ?: return
        val sideEffect = NotificationContract.Effect.ShowErrorMessage(message)
        setEffect { sideEffect }
    }
}
