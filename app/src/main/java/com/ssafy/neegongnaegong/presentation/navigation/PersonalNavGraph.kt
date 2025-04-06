package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.PersonalScreen

fun NavGraphBuilder.personalNavGraph(navController: NavController){
    navigation(
        startDestination = AppNavigation.Screen.Personal.Main.route,
        route = AppNavigation.Tab.Personal.route
    ){
        composable(AppNavigation.Screen.Personal.Main.route) {
            PersonalScreen()
//            StudiesRoute(
//                modifier = Modifier,
//                popBackStack = { },
//            )
        }
    }
}
