package com.ssafy.neegongnaegong.presentation.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.login.component.GoogleLoginButton
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors
import com.ssafy.neegongnaegong.presentation.util.GoogleSignInUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginScreen()
        }
    }
}

@Composable
fun LoginScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            GoogleSignInUtils.doGoogleSignIn(
                context = context,
                scope = scope,
                launcher = null,
                login = { Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show() })
        }

    val loginCallback: () -> Unit = {
        Log.d("LoginActivity", "Login button clicked")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightColors.BackGround
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(160.dp))
            // 앱 로고 또는 이름
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Image",
            )

            Image(
                painter = painterResource(id = R.drawable.ic_app_logo_332_81),
                contentDescription = "App Logo",
            )
            Spacer(modifier = Modifier.weight(1f))

            GoogleLoginButton(context, scope, launcher, loginCallback)

            Spacer(modifier = Modifier.height(110.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}