package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavigationGraph(
    navController: NavHostController,
    startDestination: AppNavigation.Tab,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        popExitTransition = { ExitTransition.None },
        enterTransition = { EnterTransition.None },
    ) {
        authNavGraph(navController)
        studiesNavGraph(navController)
        personalNavGraph(navController)
        calendarNavGraph(navController)
        profileNavGraph(navController)
    }
}
