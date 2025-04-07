package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppNavigation.Tab.Studies,
    ) {
        studiesNavGraph(navController)
        personalNavGraph(navController)
        calendarNavGraph(navController)
        profileNavGraph(navController)
    }
}
