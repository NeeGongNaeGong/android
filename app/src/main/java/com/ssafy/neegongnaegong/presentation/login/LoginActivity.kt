package com.ssafy.neegongnaegong.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.presentation.ui.theme.LightColors
import com.ssafy.neegongnaegong.presentation.util.GoogleSignInUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

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

            GoogleLoginButton(context, scope, launcher)

            Spacer(modifier = Modifier.height(110.dp))
        }
    }
}

@Composable
fun GoogleLoginButton(
    context: Context,
    scope: CoroutineScope,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {

    // Google 로그인 버튼
    Button(
        onClick = {
            GoogleSignInUtils.doGoogleSignIn(
                context = context,
                scope = scope,
                launcher = launcher,
                login = {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                }
            )

        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 8.dp,
            focusedElevation = 8.dp
        ),
        shape = MaterialTheme.shapes.small.copy(
            all = androidx.compose.foundation.shape.CornerSize(4.dp)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_google_logo_24_24),
                contentDescription = "Google Logo",
                modifier = Modifier
            )
            
            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "Continue with Google",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier
            )
        }
    }

}

@Preview(showBackground = true, name = "Google Login Button Preview")
@Composable
fun GoogleLoginButtonPreview() {
    val context = LocalContext.current

    // 2) Preview에서 사용 가능한 가짜 CoroutineScope
    val scope = rememberCoroutineScope()

    // 3) 실제 ActivityResultLauncher 대신, null or 임시 객체
    val launcher = rememberLauncherForActivityResult(
       contract = ActivityResultContracts.StartActivityForResult()
   ) { }

    // 이 상태로 컴포저블 호출
    GoogleLoginButton(
        context = context,
        scope = scope,
        launcher = launcher
    )

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}