package com.ssafy.neegongnaegong.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.login.component.GoogleLoginButton
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongPreviews
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToMain: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState,
        navigateToMain = navigateToMain,
        onGoogleLoginSuccess = { viewModel.setEvent(LoginContract.Event.OnGoogleLoginSuccess(it)) },
        onGoogleLoginFailure = { viewModel.setEvent(LoginContract.Event.OnGoogleLoginFailure(it)) },
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    effect: Flow<LoginContract.Effect>,
    uiState: LoginContract.State,
    navigateToMain: () -> Unit,
    onGoogleLoginSuccess: (String) -> Unit,
    onGoogleLoginFailure: (Throwable) -> Unit,
) {
    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                LoginContract.Effect.NavigateToMainScreen -> navigateToMain()
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        onGoogleLoginSuccess = onGoogleLoginSuccess,
        onGoogleLoginFailure = onGoogleLoginFailure,
    )

    if (uiState.isLoading) LoadingDialog()
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onGoogleLoginSuccess: (String) -> Unit,
    onGoogleLoginFailure: (Throwable) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier =
                Modifier
                    .weight(1F)
                    .aspectRatio(1f),
            painter = painterResource(id = R.drawable.img_app_main_logo),
            contentDescription = "App Image",
        )

        GoogleLoginButton(
            modifier = Modifier.fillMaxWidth().padding(bottom = NeeGongNaeGongTheme.paddingScheme.sp5),
            onSuccess = onGoogleLoginSuccess,
            onFailure = onGoogleLoginFailure,
        )
    }
}

@NeeGongNaeGongPreviews
@Composable
fun LoginScreenPreview() {
    NeeGongNaeGongTheme {
        LoginScreen(
            onGoogleLoginSuccess = {},
            onGoogleLoginFailure = {},
        )
    }
}
