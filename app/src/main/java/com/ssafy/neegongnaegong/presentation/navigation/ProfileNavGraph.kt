package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.ProfileScreen
import com.ssafy.neegongnaegong.presentation.notification.NotificationRoute

fun NavGraphBuilder.profileNavGraph(navController: NavController){
    navigation<AppNavigation.Tab.Profile>(
        startDestination = AppNavigation.Screen.Profile.Main(-1),
    ){
        composable<AppNavigation.Screen.Profile.Main> {
            ProfileScreen()
        }

        composable<AppNavigation.Screen.Profile.Notification> {
            NotificationRoute()
        }
    }
}
