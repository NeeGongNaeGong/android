package com.ssafy.neegongnaegong.presentation.notification

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.notification.data.NotificationUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NotificationViewModel @Inject constructor(

) : BaseViewModel<NotificationContract.Event, NotificationContract.State, NotificationContract.Effect>() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val notificationList: StateFlow<PagingData<NotificationUiModel>> by lazy {
        uiState.distinctUntilChangedBy { uiState: NotificationContract.State ->
            uiState.isLoading
        }.filter { uiState: NotificationContract.State ->
            uiState.isLoading
        }.flatMapLatest {
            delay(1500)
            flowOf(TestPagingData)
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
            TODO("모든 알람을 삭제합니다.")
        }
    }

    private fun handleDeleteNotification(data: NotificationUiModel) {
        viewModelScope.safeLaunch(errorContext = NotificationContract.Error.ShowErrorMessage) {
            TODO("특정 알람을 삭제합니다. ${data.id}")
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

    companion object {
        val TestModel = NotificationUiModel(
            id = Random.nextLong(),
            image = "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTRI8A6M23RTePWn8of5fgwRzSMMzRy_6mZP7OrP79VF3ByzCoRcyfx6bYr9w4bH9zdVfpV_LP9hBAudM5SRGyjnbbEhnrs2vWZKF8wySI",
            user = "킹 민 조",
            content = "님이 길을 나선다 모두 머리를 조아려라",
            isRead = Random.nextBoolean()
        )
        val TestList = List<NotificationUiModel>(500) { TestModel }
        val TestPagingData = PagingData.from(TestList)
    }
}
