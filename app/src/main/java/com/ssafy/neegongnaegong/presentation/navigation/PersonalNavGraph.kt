package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.presentation.group.user.search.UserSearchRoute
import com.ssafy.neegongnaegong.presentation.personal.edit.StudyRecordEditRoute

fun NavGraphBuilder.personalNavGraph(navController: NavController) {
    navigation<AppNavigation.Tab.Personal>(
        startDestination = AppNavigation.Screen.Personal.Main,
    ) {
        composable<AppNavigation.Screen.Personal.Main> {

            UserSearchRoute { }

//            PersonalRoute(
//                popBackStack = { navController.popBackStack() },
//                navigateToEditScreen = {
//                    navController.navigate(
//                        AppNavigation.Screen.Personal.Edit(
//                            it,
//                        ),
//                    )
//                },
//                navController = navController,
//            )
        }

        composable<AppNavigation.Screen.Personal.Edit> { backStackEntry ->
            val route = backStackEntry.toRoute<AppNavigation.Screen.Personal.Edit>()
            StudyRecordEditRoute(
                popBackStack = {
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "refreshNeeded",
                        true,
                    )
                    navController.popBackStack()
                },
                studyRecordId = route.studyRecordId,
            )
        }
    }
}
