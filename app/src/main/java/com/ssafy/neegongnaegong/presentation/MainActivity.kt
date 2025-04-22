package com.ssafy.neegongnaegong.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ssafy.neegongnaegong.presentation.splash.SplashViewModel
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { viewModel.isLoading }

        enableEdgeToEdge()

        setContent {
            NeeGongNaeGongTheme(dynamicColor = false) {
                MainScreen()
            }
        }
    }
}
