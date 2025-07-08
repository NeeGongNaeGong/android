package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavigationGraph(navController: NavHostController) {
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
