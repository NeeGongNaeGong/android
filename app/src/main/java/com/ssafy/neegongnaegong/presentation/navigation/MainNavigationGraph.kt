package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.neegongnaegong.presentation.splash.SplashViewModel
import com.ssafy.neegongnaegong.presentation.util.AuthDestinationManager

@Composable
fun MainNavigationGraph(navController: NavHostController, viewModel: SplashViewModel = hiltViewModel()) {
    val destination by AuthDestinationManager.destination.collectAsStateWithLifecycle(null)
    val state by viewModel.state.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = "splash",
    ) {
        // 아무것도 없고 1회성인 화면을 위해서 AppNavigation에 splash screen 관련 Annotation 만들기도 뭐해서
        // 그냥 string으로 대충 지었는데 AppNavigaton에 만들까요..?
        composable("splash"){
            /*
            UI 없이 isLoading이 true로 즉 로딩되는 동안 계속해서 여기에 위치해 있다가
            isLoading이 끝나는 순간 isLoginSuccess의 값에 따라 Login or Studies로 이동하도록
             */
            LaunchedEffect(state.isLoading) {
                if (!state.isLoading) {
                    navController.navigate(
                        if (state.isLoginSuccess) AppNavigation.Tab.Studies else AppNavigation.Tab.Auth
                    ) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }
        }
        authNavGraph(navController)
        studiesNavGraph(navController)
        personalNavGraph(navController)
        calendarNavGraph(navController)
        profileNavGraph(navController)
    }
}
