package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.PersonalScreen

fun NavGraphBuilder.personalNavGraph(navController: NavController){
    navigation<AppNavigation.Tab.Personal>(
        startDestination = AppNavigation.Screen.Personal.Main
    ){
        composable<AppNavigation.Screen.Personal.Main> {
            PersonalScreen()
//            StudiesRoute(
//                modifier = Modifier,
//                popBackStack = { },
//            )
        }
    }
}
