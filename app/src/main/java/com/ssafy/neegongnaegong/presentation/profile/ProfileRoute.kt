package com.ssafy.neegongnaegong.presentation.profile

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent.Builder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.base.CollectSideEffects
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.profile.data.ProfileUiModel
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager

private const val PRIVACY_INFO_URL = "https://www.notion.so/2082fdb33c0980419574e74d7ee3dcaf"

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToNotification: () -> Unit,
    navigateToAuth: () -> Unit,
) {
    val context: Context = LocalContext.current
    val uiState: ProfileContract.State by viewModel.uiState.collectAsStateWithLifecycle()
    val uiModel: ProfileUiModel by viewModel.uiModel.collectAsStateWithLifecycle()

    CollectSideEffects(effectFlow = viewModel.effect) { effect: ProfileContract.Effect ->
        when (effect) {
            is ProfileContract.Effect.ShowErrorMessage -> {
                SnackbarManager.showErrorMessage(message = effect.message)
            }

            ProfileContract.Effect.ShowDuplicatedNicknameErrorMessage -> {
                val message: String = context.getString(R.string.already_use_nickname)
                SnackbarManager.showErrorMessage(message = message)
            }

            ProfileContract.Effect.ShowInvalidNicknameErrorMessage -> {
                val message: String = context.getString(R.string.invalid_nickname)
                SnackbarManager.showErrorMessage(message = message)
            }

            ProfileContract.Effect.NavigateToNotification -> {
                navigateToNotification()
            }

            ProfileContract.Effect.NavigateToPrivacyInfo -> {
                Builder()
                    .build()
                    .launchUrl(context, PRIVACY_INFO_URL.toUri())
            }

            ProfileContract.Effect.NavigateToAuth -> {
                navigateToAuth()
            }
        }
    }

    if (uiState.isInitial) {
        LoadingDialog()
    } else {
        ProfileScreen(
            profileImg = uiModel.profileImg,
            nickname = uiModel.nickname,
            hasUnReadNotification = uiModel.hasUnReadNotification,
            shouldShowProfileImageWarningInfo = uiModel.shouldShowProfileImageWarningInfo,
            isEditing = uiState.isEditing,
            onCheckProfileImageWarning = {
                val event = ProfileContract.Event.CheckProfileImageWarning
                viewModel.setEvent(event = event)
            },
            onChangeNickName = { text: String ->
                val event = ProfileContract.Event.ChangeNickName(text = text)
                viewModel.setEvent(event = event)
            },
            onClickEdit = {
                val event = ProfileContract.Event.ClickEdit
                viewModel.setEvent(event = event)
            },
            onClickEditCancel = {
                val event = ProfileContract.Event.ClickEditCancel
                viewModel.setEvent(event = event)
            },
            onImageSelected = { uri: Uri ->
                val event = ProfileContract.Event.ChangeImage(uri = uri)
                viewModel.setEvent(event = event)
            },
            onClickNotification = {
                val event = ProfileContract.Event.ClickNotification
                viewModel.setEvent(event = event)
            },
            onClickPrivacyInfo = {
                val event = ProfileContract.Event.ClickPrivacyInfo
                viewModel.setEvent(event = event)
            },
            onClickLogout = {
                val event = ProfileContract.Event.ClickLogout
                viewModel.setEvent(event = event)
            },
            onClickDeleteAccount = {
                val event = ProfileContract.Event.ClickDeleteAccount
                viewModel.setEvent(event = event)
            },
        )
    }

    if (uiState.isModifying) LoadingDialog()
}
