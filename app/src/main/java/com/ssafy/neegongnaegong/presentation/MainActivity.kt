package com.ssafy.neegongnaegong.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ssafy.neegongnaegong.presentation.base.LoginStatusViewModel
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
            NeeGongNaeGongTheme {
                Surface(color = NeeGongNaeGongTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}
