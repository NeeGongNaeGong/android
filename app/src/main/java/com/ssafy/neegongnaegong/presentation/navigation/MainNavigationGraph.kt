package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ssafy.neegongnaegong.presentation.util.AuthDesintaionManager

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    val destination by AuthDesintaionManager.destination.collectAsStateWithLifecycle(null)

    destination?.let { startDestination ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            loginNavGraph(navController)
            studiesNavGraph(navController)
            personalNavGraph(navController)
            calendarNavGraph(navController)
            profileNavGraph(navController)
        }
    }
}
