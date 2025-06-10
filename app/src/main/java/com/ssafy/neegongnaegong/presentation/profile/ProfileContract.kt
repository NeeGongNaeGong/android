package com.ssafy.neegongnaegong.presentation.profile

import android.net.Uri
import androidx.compose.runtime.Stable
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

interface ProfileContract {

    sealed class Event : UiEvent {
        data object ClickNotification : Event()
        data object ClickPrivacyInfo : Event()
        data object ClickLogout : Event()
        data object ClickDeleteAccount : Event()
        data object ClickEdit : Event()
        data object ClickEditCancel : Event()
        data class ChangeImage(val uri: Uri) : Event()
        data class ChangeNickName(val text: String) : Event()
    }

    @Stable
    data class State(
        val isInitial: Boolean = true,
        val isModifying: Boolean = false,
        val isEditing: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorMessage(val message: String) : Effect()
        data object ShowInvalidNicknameErrorMessage : Effect()
        data object ShowDuplicatedNicknameErrorMessage : Effect()
        data object NavigateToNotification : Effect()
        data object NavigateToPrivacyInfo : Effect()
        data object NavigateToAuth : Effect()
    }

    sealed class Error : ErrorContext {
        data object CantAccessMyInfoError: Error()
        data object CantAccessUnReadNotificationInfoError: Error()
        data object LogoutError : Error()
        data object DeleteAccountError: Error()
        data object ChangeNicknameError: Error()
        data object ChangeProfileImgError: Error()
    }
}
