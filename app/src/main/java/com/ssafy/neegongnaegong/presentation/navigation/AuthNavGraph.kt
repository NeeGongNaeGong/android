package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.login.LoginRoute

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<AppNavigation.Tab.Auth>(
        startDestination = AppNavigation.Screen.Auth.Login
    ){
        composable<AppNavigation.Screen.Auth.Login> {
            LoginRoute()
        }
    }
}
