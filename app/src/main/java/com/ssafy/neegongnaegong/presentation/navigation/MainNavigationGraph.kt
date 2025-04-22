package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ssafy.neegongnaegong.presentation.util.AuthDestinationManager

@Composable
fun MainNavigationGraph(isLoginSuccess: Boolean = false, navController: NavHostController) {
    val destination by AuthDestinationManager.destination.collectAsStateWithLifecycle(null)

    NavHost(
        navController = navController,
        startDestination = if (isLoginSuccess) AppNavigation.Tab.Studies else AppNavigation.Login,
    ) {
        loginNavGraph(navController)
        studiesNavGraph(navController)
        personalNavGraph(navController)
        calendarNavGraph(navController)
        profileNavGraph(navController)
    }
}
