package com.ssafy.neegongnaegong.presentation.profile

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.usecase.user.GetMyProfileUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.profile.data.ProfileMapper.toUiModel
import com.ssafy.neegongnaegong.presentation.profile.data.ProfileUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getMyProfileUseCase: GetMyProfileUseCase
) : BaseViewModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect>() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiModel: StateFlow<ProfileUiModel> by lazy {
        uiState.distinctUntilChangedBy { uiState: ProfileContract.State ->
            uiState.isInitial
        }.filter { uiState: ProfileContract.State ->
            uiState.isInitial
        }.safeFlatMapLatest(errorContext = ProfileContract.Error.ShowErrorMessage) {
            getMyProfileUseCase()
        }.map { user: User ->
            user.toUiModel()
        }.onEach {
            endLoad()
        }.toViewModelState(ProfileUiModel.default())
    }

    override fun createInitialState(): ProfileContract.State = ProfileContract.State()

    override fun handleEvent(event: ProfileContract.Event) = when (event) {
        ProfileContract.Event.ClickNotification -> handleNotification()
        ProfileContract.Event.ClickNotice -> handleNotice()
        ProfileContract.Event.ClickPrivacyInfo -> handlePrivacyInfo()
        ProfileContract.Event.ClickDeleteAccount -> handleDeleteAccount()
    }

    private fun handleNotification() {
        val sideEffect = ProfileContract.Effect.NavigateToNotification
        setEffect { sideEffect }
    }

    private fun handleNotice() {
        val sideEffect = ProfileContract.Effect.NavigateToNotice
        setEffect { sideEffect }
    }

    private fun handlePrivacyInfo() {
        val sideEffect = ProfileContract.Effect.NavigateToPrivacyInfo
        setEffect { sideEffect }
    }

    private fun handleDeleteAccount() {
        viewModelScope.safeLaunch {
            // TODO("계정 삭제")
            delay(1000)
            val sideEffect = ProfileContract.Effect.NavigateToLogout
            setEffect { sideEffect }
        }
    }

    override fun handleException(e: Throwable, errorContext: ErrorContext, retry: () -> Unit) {
        endLoad()

        val message: String = e.message ?: return
        val sideEffect = ProfileContract.Effect.ShowErrorMessage(message)
        setEffect { sideEffect }
    }

    private fun setModify() {
        setState { copy(isModifying = true) }
    }

    private fun endLoad() {
        setState { copy(isInitial = false, isModifying = false) }
    }
}
