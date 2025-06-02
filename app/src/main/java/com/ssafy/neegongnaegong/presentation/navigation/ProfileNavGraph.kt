package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.notification.NotificationRoute
import com.ssafy.neegongnaegong.presentation.profile.ProfileRoute

fun NavGraphBuilder.profileNavGraph(navController: NavController) {
    navigation<AppNavigation.Tab.Profile>(
        startDestination = AppNavigation.Screen.Profile.Main,
    ) {
        composable<AppNavigation.Screen.Profile.Main> {
            ProfileRoute(
                navigateToNotification = {
                    val notification = AppNavigation.Screen.Profile.Notification
                    navController.navigate(route = notification)
                },
                navigateToAuth = {
                    val auth = AppNavigation.Screen.Auth.Login
                    navController.navigate(route = auth) {
                        popUpTo(id = 0)
                    }
                }
            )
        }

        composable<AppNavigation.Screen.Profile.Notification> {
            NotificationRoute()
        }
    }
}
