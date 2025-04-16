package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ssafy.neegongnaegong.presentation.login.LoginRoute

fun NavGraphBuilder.loginNavGraph(navController: NavController) {
    composable<AppNavigation.Login> {
        LoginRoute()
    }
}
