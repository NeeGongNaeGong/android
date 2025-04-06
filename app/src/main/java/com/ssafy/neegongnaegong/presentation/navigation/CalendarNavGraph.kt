package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.CalendarScreen

fun NavGraphBuilder.calendarNavGraph(navController: NavController){
    navigation(
        startDestination = AppNavigation.Screen.Calendar.Main.route,
        route = AppNavigation.Tab.Calendar.route
    ){
        composable(AppNavigation.Screen.Calendar.Main.route) {
            CalendarScreen()
        }
    }
}
