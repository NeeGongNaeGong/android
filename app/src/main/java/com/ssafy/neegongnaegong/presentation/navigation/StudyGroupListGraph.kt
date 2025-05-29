package com.ssafy.neegongnaegong.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteListRoute

fun NavGraphBuilder.studyGroupListNavGraph(
    navController: NavController,
    groupId: Long,
) {
    navigation<AppNavigation.Screen.Studies.List.Tab.Notice>(
        startDestination = AppNavigation.Screen.Studies.List.Screen.NoticeList(groupId),
    ) {
        composable<AppNavigation.Screen.Studies.List.Screen.NoticeList> {
        }
        composable<AppNavigation.Screen.Studies.List.Screen.NoticeDetail> {
        }
    }

    navigation<AppNavigation.Screen.Studies.List.Tab.Vote>(
        startDestination = AppNavigation.Screen.Studies.List.Screen.VoteList(groupId),
    ) {
        composable<AppNavigation.Screen.Studies.List.Screen.VoteList> {
            VoteListRoute(
                backStackEntry = it,
                viewModel = hiltViewModel(it),
            ) {
                val result = navController.popBackStack()
                result
            }
        }
        composable<AppNavigation.Screen.Studies.List.Screen.VoteDetail> {
        }
    }
}

enum class Index(
    val index: Int,
    val title: String,
    val route: AppNavigation.Screen.Studies.List.Tab,
) {
    Notice(
        index = 0,
        title = "공지",
        route = AppNavigation.Screen.Studies.List.Tab.Notice,
    ),
    Vote(
        index = 1,
        title = "투표",
        route = AppNavigation.Screen.Studies.List.Tab.Vote,
    ),
}
