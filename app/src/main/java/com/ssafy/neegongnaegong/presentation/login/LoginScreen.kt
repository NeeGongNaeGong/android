package com.ssafy.neegongnaegong.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.component.LoadingDialog
import com.ssafy.neegongnaegong.presentation.login.component.GoogleLoginButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginContent(
        modifier = modifier,
        effect = viewModel.effect,
        uiState = uiState,
        onGoogleLoginSuccess = { viewModel.setEvent(LoginContract.Event.OnGoogleLoginSuccess(it)) },
        onGoogleLoginFailure = { viewModel.setEvent(LoginContract.Event.OnGoogleLoginFailure(it)) }
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    effect: Flow<LoginContract.Effect>,
    uiState: LoginContract.State,
    onGoogleLoginSuccess: (String) -> Unit,
    onGoogleLoginFailure: (Throwable) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(effect) {
        effect.collectLatest { effect ->
            when (effect) {
                is LoginContract.Effect.ShowErrorSnackBar -> scope.launch {
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        actionLabel = "확인",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        onGoogleLoginSuccess = onGoogleLoginSuccess,
        onGoogleLoginFailure = onGoogleLoginFailure
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
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(160.dp))

        Image(
            modifier = Modifier.aspectRatio(1f).fillMaxWidth(),
            painter = painterResource(id = R.drawable.img_app_main_logo),
            contentDescription = "App Image",
        )

        Spacer(modifier = Modifier.weight(1f))

        GoogleLoginButton(
            onSuccess = onGoogleLoginSuccess,
            onFailure = onGoogleLoginFailure
        )

        Spacer(modifier = Modifier.height(110.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onGoogleLoginSuccess = {},
        onGoogleLoginFailure = {}
    )
}
