package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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
                },
            )
        }

        composable<AppNavigation.Screen.Profile.Notification> {
            NotificationRoute(
                navigateUp = navController::navigateUp,
                navigateToGroup = { groupId: Long ->
                    with(navController) {
                        navigate(AppNavigation.Tab.Studies) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        navigate(AppNavigation.Screen.Studies.StudiesDetail(studyGroupId = groupId))
                    }
                },
                navigateToNotice = { groupId: Long, noticeId: Long ->
                    with(navController) {
                        navigate(AppNavigation.Tab.Studies) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        navigate(AppNavigation.Screen.Studies.StudiesDetail(studyGroupId = groupId))
                        navigate(AppNavigation.Screen.Studies.SubTab.Main(0, groupId))
                        navigate(
                            AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail(
                                studyGroupId = groupId,
                                noticeId = noticeId,
                            ),
                        )
                    }
                },
                navigateToVote = { groupId: Long, voteId: Long ->
                    with(navController) {
                        navigate(AppNavigation.Tab.Studies) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        navigate(AppNavigation.Screen.Studies.StudiesDetail(studyGroupId = groupId))
                        navigate(AppNavigation.Screen.Studies.SubTab.Main(1, groupId))
                        navigate(
                            AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail(
                                studyGroupId = groupId,
                                voteId = voteId,
                            ),
                        )
                    }
                },
            )
        }
    }
}
