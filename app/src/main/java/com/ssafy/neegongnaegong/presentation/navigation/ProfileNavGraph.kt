package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.ProfileScreen

fun NavGraphBuilder.profileNavGraph(navController: NavController){
    navigation(
        startDestination = AppNavigation.Screen.Profile.Main.route,
        route = AppNavigation.Tab.Profile.createRoute(-1)
    ){
        composable(AppNavigation.Screen.Profile.Main.route) {
            ProfileScreen()
        }
    }
}
