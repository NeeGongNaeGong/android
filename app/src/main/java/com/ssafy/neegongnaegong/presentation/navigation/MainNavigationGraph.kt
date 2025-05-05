package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.neegongnaegong.presentation.base.LoginStatusViewModel

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    viewModel: LoginStatusViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    /*
    UI 없이 isLoading이 true로 즉 로딩되는 동안 계속해서 여기에 위치해 있다가
    isLoading이 끝나는 순간 isLoginSuccess의 값에 따라 Login or Studies로 이동하도록
     */
    LaunchedEffect(state) {
        if (!state.isLoading) {
            navController.navigate(
                if (state.isLoginSuccess) AppNavigation.Tab.Studies else AppNavigation.Tab.Auth
            ) {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "splash",
    ) {
        composable("splash") {}
        authNavGraph(navController)
        studiesNavGraph(navController)
        personalNavGraph(navController)
        calendarNavGraph(navController)
        profileNavGraph(navController)
    }
}
