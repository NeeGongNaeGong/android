package com.ssafy.neegongnaegong.presentation.login.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.ssafy.neegongnaegong.presentation.util.GoogleAuthUtils
import kotlinx.coroutines.launch

@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit,
    onFailure: (Throwable) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    fun onClick() {
        scope.launch {
            GoogleAuthUtils.signIn(context, onSuccess, onFailure)
        }
    }

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp),
        onClick = { onClick() },
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
    GoogleLoginButton(
        onSuccess = {},
        onFailure = {}
    )
}
