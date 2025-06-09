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
            // TODO(다른 화면들과 결합되면 수정해야 합니다.)
            NotificationRoute(
                navigateUp = navController::navigateUp,
                navigateToGroup = { groupId: Long ->
                    val group = AppNavigation.Screen.Studies.StudiesDetail
                    navController.navigate(route = group)
                },
                navigateToNotice = { groupId: Long, noticeId: Long ->

                },
                navigateToVote = { groupId: Long, voteId: Long ->

                }
            )
        }
    }
}
