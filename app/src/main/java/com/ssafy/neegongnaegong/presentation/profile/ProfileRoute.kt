package com.ssafy.neegongnaegong.presentation.profile

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.presentation.base.CollectSideEffects
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.profile.data.ProfileUiModel
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToNotification: () -> Unit,
    navigateToAuth: () -> Unit
) {
    val context: Context = LocalContext.current
    val uiState: ProfileContract.State by viewModel.uiState.collectAsStateWithLifecycle()
    val uiModel: ProfileUiModel by viewModel.uiModel.collectAsStateWithLifecycle()

    CollectSideEffects(effectFlow = viewModel.effect) { effect: ProfileContract.Effect ->
        when (effect) {
            is ProfileContract.Effect.ShowErrorMessage -> {
                SnackbarManager.showErrorMessage(message = effect.message)
            }

            ProfileContract.Effect.NavigateToNotification -> {
                navigateToNotification()
            }

            ProfileContract.Effect.NavigateToNotice -> {
                // TODO ("주소 변경 필요!")
                val url = "https://www.naver.com"
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(context, url.toUri())
            }

            ProfileContract.Effect.NavigateToPrivacyInfo -> {
                // TODO ("주소 변경 필요!")
                val url = "https://www.google.com"
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(context, url.toUri())
            }

            ProfileContract.Effect.NavigateToLogout -> {
                navigateToAuth()
            }
        }
    }

    if (uiState.isInitial or uiState.isModifying) LoadingDialog()
    else ProfileScreen(
        profileImg = uiModel.profileImg,
        nickname = uiModel.nickname,
        isEditing = uiState.isEditing,
        onChangeNickName = { text: String ->
            val event = ProfileContract.Event.ChangeNickName(text = text)
            viewModel.setEvent(event = event)
        },
        onClickNotification = {
            val event = ProfileContract.Event.ClickNotification
            viewModel.setEvent(event = event)
        },
        onClickNotice = {
            val event = ProfileContract.Event.ClickNotice
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
