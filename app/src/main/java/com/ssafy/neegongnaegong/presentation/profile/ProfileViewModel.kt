package com.ssafy.neegongnaegong.presentation.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.exception.DuplicateNicknameException
import com.ssafy.neegongnaegong.domain.exception.InvalidNicknameException
import com.ssafy.neegongnaegong.domain.exception.UnsupportedFileTypeException
import com.ssafy.neegongnaegong.domain.model.User
import com.ssafy.neegongnaegong.domain.usecase.user.CheckUnReadNotificationUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.GetMyProfileUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.LogoutUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.SaveProfileImageWarningAcceptedAtUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.ShouldShowProfileImageWarningUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.UpdateNicknameUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.UpdateProfileImageUseCase
import com.ssafy.neegongnaegong.domain.usecase.user.WithdrawUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.profile.data.ProfileMapper.toUiModel
import com.ssafy.neegongnaegong.presentation.profile.data.ProfileUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val getMyProfileUseCase: GetMyProfileUseCase,
        private val updateNicknameUseCase: UpdateNicknameUseCase,
        private val updateProfileImageUseCase: UpdateProfileImageUseCase,
        private val checkUnReadNotificationUseCase: CheckUnReadNotificationUseCase,
        private val logoutUseCase: LogoutUseCase,
        private val withdrawUseCase: WithdrawUseCase,
        private val shouldShowProfileImageWarningUseCase: ShouldShowProfileImageWarningUseCase,
        private val saveProfileImageWarningAcceptedAtUseCase: SaveProfileImageWarningAcceptedAtUseCase,
    ) : BaseViewModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect>() {
        @OptIn(ExperimentalCoroutinesApi::class)
        val uiModel: StateFlow<ProfileUiModel> by lazy {
            combine(
                myInfo,
                hasUnReadNotification,
                showProfileImageWarning,
            ) { user: User, hasUnReadNotification: Boolean, showProfileImageWarning: Boolean ->
                user.toUiModel(
                    hasUnReadNotification = hasUnReadNotification,
                    shouldShowProfileImageWarningInfo = showProfileImageWarning,
                )
            }.onEach {
                endLoad()
            }.toViewModelState(ProfileUiModel.default())
        }

        private val myInfo: Flow<User> by lazy {
            uiState.init().safeFlatMapLatest(
                errorContext = ProfileContract.Error.CantAccessMyInfoError,
                transform = { getMyProfileUseCase() },
            )
        }

        private val hasUnReadNotification: Flow<Boolean> by lazy {
            uiState.init().safeFlatMapLatest(
                errorContext = ProfileContract.Error.CantAccessUnReadNotificationInfoError,
                transform = { checkUnReadNotificationUseCase() },
            )
        }

        private val showProfileImageWarning: Flow<Boolean> by lazy {
            uiState.init().safeFlatMapLatest(
                errorContext = ProfileContract.Error.CantAccessShowProfileImageWarningInfoError,
                transform = { shouldShowProfileImageWarningUseCase() },
            )
        }

        override fun createInitialState(): ProfileContract.State = ProfileContract.State()

        override fun handleEvent(event: ProfileContract.Event) =
            when (event) {
                ProfileContract.Event.ClickNotification -> handleNotification()
                ProfileContract.Event.ClickInquiry -> handleInquiry()
                ProfileContract.Event.ClickPrivacyInfo -> handlePrivacyInfo()
                ProfileContract.Event.ClickLogout -> handleLogout()
                ProfileContract.Event.ClickDeleteAccount -> handleDeleteAccount()
                ProfileContract.Event.ClickEdit -> handleEdit(isEditing = true)
                ProfileContract.Event.ClickEditCancel -> handleEdit(isEditing = false)
                is ProfileContract.Event.ChangeNickName -> handleChangeNickName(event.text)
                is ProfileContract.Event.ChangeImage -> handleChangeProfileImage(event.uri)
                ProfileContract.Event.CheckProfileImageWarning -> handleCheckProfileImageWarning()
            }

        private fun handleNotification() {
            val sideEffect = ProfileContract.Effect.NavigateToNotification
            setEffect { sideEffect }
        }

        private fun handleInquiry() {
            val sideEffect = ProfileContract.Effect.NavigateToInquiry
            setEffect { sideEffect }
        }

        private fun handlePrivacyInfo() {
            val sideEffect = ProfileContract.Effect.NavigateToPrivacyInfo
            setEffect { sideEffect }
        }

        private fun handleEdit(isEditing: Boolean) {
            setState { copy(isEditing = isEditing) }
        }

        private fun handleCheckProfileImageWarning() {
            viewModelScope.safeLaunch(errorContext = ProfileContract.Error.ChangeProfileImageWarningInfoError) {
                saveProfileImageWarningAcceptedAtUseCase()
                    .withLoading { isModifying ->
                        setState { copy(isModifying = isModifying) }
                    }.firstOrNull()
            }
        }

        private fun handleLogout() {
            viewModelScope.safeLaunch(errorContext = ProfileContract.Error.LogoutError) {
                logoutUseCase()
                    .withLoading { isModifying ->
                        setState { copy(isModifying = isModifying) }
                    }.firstOrNull()

                val sideEffect = ProfileContract.Effect.NavigateToAuth
                setEffect { sideEffect }
            }
        }

        private fun handleDeleteAccount() {
            viewModelScope.safeLaunch(errorContext = ProfileContract.Error.DeleteAccountError) {
                withdrawUseCase()
                    .withLoading { isModifying ->
                        setState { copy(isModifying = isModifying) }
                    }.firstOrNull()

                val sideEffect = ProfileContract.Effect.NavigateToAuth
                setEffect { sideEffect }
            }
        }

        private fun handleChangeNickName(text: String) {
            viewModelScope.safeLaunch(errorContext = ProfileContract.Error.ChangeNicknameError) {
                updateNicknameUseCase(nickname = text)
                    .withLoading { isModifying ->
                        setState { copy(isModifying = isModifying) }
                    }.firstOrNull()

                setState { copy(isEditing = false) }
            }
        }

        private fun handleChangeProfileImage(uri: Uri) {
            viewModelScope.safeLaunch(errorContext = ProfileContract.Error.ChangeProfileImgError) {
                val userId: Long = uiModel.value.id
                updateProfileImageUseCase(userId = userId, url = uri.toString())
                    .withLoading { isModifying ->
                        setState { copy(isModifying = isModifying) }
                    }.firstOrNull()
            }
        }

        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            endLoad()

            val sideEffect: ProfileContract.Effect =
                when (e) {
                    is DuplicateNicknameException -> {
                        ProfileContract.Effect.ShowDuplicatedNicknameErrorMessage
                    }

                    is InvalidNicknameException -> {
                        ProfileContract.Effect.ShowInvalidNicknameErrorMessage
                    }

                    is UnsupportedFileTypeException -> {
                        ProfileContract.Effect.ShowUnsupportedFileTypeErrorMessage
                    }

                    else -> {
                        val message: String = e.message ?: return
                        ProfileContract.Effect.ShowErrorMessage(message)
                    }
                }
            setEffect { sideEffect }
        }

        private fun endLoad() {
            setState { copy(isInitial = false, isModifying = false) }
        }

        private fun <T> Flow<T>.init() =
            uiState
                .distinctUntilChangedBy { uiState: ProfileContract.State ->
                    uiState.isInitial
                }.filter { uiState: ProfileContract.State ->
                    uiState.isInitial
                }
    }
