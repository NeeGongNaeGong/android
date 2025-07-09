package com.ssafy.neegongnaegong.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.ssafy.neegongnaegong.presentation.base.LoginStatusViewModel
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import com.ssafy.neegongnaegong.presentation.ui.theme.NeeGongNaeGongTheme
import com.ssafy.neegongnaegong.presentation.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: LoginStatusViewModel by viewModels()

    private val permissions = PermissionManager.getPermissions()

    private val permissionLauncher by lazy {
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ -> }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        /**
         * setKeeponScreenCondition이 만족할 때 계속 Splash 스크린이 켜져 있지만
         * 조건과 별개로 뒤에서 아래의 setContent들은 실행됨
         * 이로 인해서 값에 따라 Splash Screen이 끝난 뒤에도
         * 리렌더링이 깜박 거리면서 발생했었음
         * => 기존에 destination?.let으로 NavHost를 감쌌으나 조금 개선해보고자 개선 시도
         *
         * => 이를 isLoading과 isLoginSuccess를 MutableStateFlow로 하나로 같이 관리
         * => startDestination을 Splash라는 경로로 지정하고, 해당 경로에서 아무런 UI 없이 LaunchedEffect로
         * isLoading을 관찰하면서, isLoading이 풀리는 순간 isLoginSuccess에 따라서 navigate 시키는 로직으로 작성
         * => NavHost를 ?.let으로 감싸지 않고, login값에 따라 startDestination이 변하지도 않으면서
         * => 깜박거림도 없이 이동되도록 함
         * => NavHost 리렌더링이 일어나는 대신 그냥 Navigation으로 화면이 전환되도록 함
         */
        splashScreen.setKeepOnScreenCondition { viewModel.state.value.isLoading }

        PermissionManager.requestPermissions(this, permissionLauncher, permissions)

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            val state by viewModel.state.collectAsStateWithLifecycle()

            /*
            UI 없이 isLoading이 true로 즉 로딩되는 동안 계속해서 여기에 위치해 있다가
            isLoading이 끝나는 순간 isLoginSuccess의 값에 따라 Login or Studies로 이동하도록
             */
            LaunchedEffect(state) {
                println("싸피 $state $intent")
                if (!state.isLoading) {
                    // 알림을 눌러서 딥링크를 통해 들어온게 아닐 때
                    if (intent.data == null) {
                        navController.navigate(
                            if (state.isLoginSuccess) AppNavigation.Tab.Studies else AppNavigation.Tab.Auth,
                        ) {
                            popUpTo("splash") { inclusive = true }
                        }
                    } else {
                        if (state.isLoginSuccess) {
                            navController.handleDeepLink(intent)
                        } else {
                            navController.navigate(AppNavigation.Tab.Auth) {
                                popUpTo("splash") { inclusive = false }
                            }
                        }
                    }
                }
            }

            NeeGongNaeGongTheme {
                Surface(color = NeeGongNaeGongTheme.colorScheme.background) {
                    MainScreen(navController)
                }
            }
        }
    }
}
