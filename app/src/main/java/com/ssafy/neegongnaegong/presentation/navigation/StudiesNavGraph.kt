package com.ssafy.neegongnaegong.presentation.navigation

import VotedPersonListRoute
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteStatusInfo
import com.ssafy.neegongnaegong.presentation.group.StudiesDetailRoute
import com.ssafy.neegongnaegong.presentation.group.StudiesRoute
import com.ssafy.neegongnaegong.presentation.group.list.main.ListRoute
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailRoute
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailRoute
import com.ssafy.neegongnaegong.presentation.group.management.StudiesManagementRoute
import com.ssafy.neegongnaegong.presentation.group.notice.NoticeRoute
import com.ssafy.neegongnaegong.presentation.group.record.RecordRoute
import com.ssafy.neegongnaegong.presentation.group.vote.VoteRoute
import kotlinx.serialization.json.Json

/**
 * startDestination은 여기 Graph에서 최초로 띄울 화면의 경로
 * route는 이 NavGraph를 가리키는 이름
 * navController는 나중에 네비게이트로 이동할 때 용도
 * 여기가 Study Nav Graph이지만
 * 모든 경로의 이름이 BottomNavigation.Screen.~~.route로 다 들어가 있기 때문에
 * 여기서 Profile 탭의 화면의 경로도 BottomNavigation으로 접근해서 사용할 수 있다.
 * Study 탭의 Navigation을 설정하는 곳
 */
fun NavGraphBuilder.studiesNavGraph(navController: NavController) {
    navigation<AppNavigation.Tab.Studies>(
        startDestination = AppNavigation.Screen.Studies.Main,
    ) {
        composable<AppNavigation.Screen.Studies.Main> {
//            StudiesScreen()
            StudiesRoute(
                modifier = Modifier,
                popBackStack = { },
                navigateToStudiesDetail = {
                    navController.navigate(AppNavigation.Screen.Studies.StudiesDetail)
                },
                navigateToStudiesManagement = {
                    navController.navigate(AppNavigation.Screen.Studies.Management)
                },
            )
        }

        composable<AppNavigation.Screen.Studies.StudiesDetail> {
            StudiesDetailRoute(
                modifier = Modifier,
                popBackStack = navController::popBackStack,
            )
        }
        composable<AppNavigation.Screen.Studies.Management> {
            StudiesManagementRoute(
                modifier = Modifier,
                popBackStack = navController::popBackStack,
            )
        }

        composable<AppNavigation.Screen.Studies.MakeVote> {
            VoteRoute(
                navigateToMain = { startTab, groupId ->
                    navController.navigate(
                        AppNavigation.Screen.Studies.SubTab.Main(
                            startTab,
                            groupId,
                        ),
                    ) {
                        popUpTo<AppNavigation.Screen.Studies.SubTab.Main> {
                            inclusive = true
                        }
                    }
                },
                popBackStack = navController::popBackStack,
            )
        }

        composable<AppNavigation.Screen.Studies.MakeNotice> {
            NoticeRoute(
                popBackStackInclusive = { startTab, groupId ->
                    navController.navigate(
                        AppNavigation.Screen.Studies.SubTab.Main(
                            startTab,
                            groupId,
                        ),
                    ) {
                        popUpTo<AppNavigation.Screen.Studies.SubTab.Main> {
                            inclusive = true
                        }
                    }
                },
                popBackStack = navController::popBackStack,
            )
        }

        composable<AppNavigation.Screen.Studies.Record> { backStackEntry ->
            val (groupId, memberId) = backStackEntry.toRoute<AppNavigation.Screen.Studies.Record>()
            RecordRoute(
                groupId = groupId,
                memberId = memberId,
                popBackStack = navController::popBackStack,
            )
        }

        composable<AppNavigation.Screen.Studies.SubTab.Main> { backStackEntry ->
            val (startTabIndex, groupId) = backStackEntry.toRoute<AppNavigation.Screen.Studies.SubTab.Main>()
            ListRoute(
                popBackStack = navController::popBackStack,
                startTabIdx = startTabIndex,
                navigateToNoticeDetail = {
                    navController.navigate(
                        AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail(groupId, it),
                    ) {
                        popUpTo<AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail> {
                            inclusive = false
                        }
                    }
                },
                navigateToVoteDetail = {
                    navController.navigate(
                        AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail(groupId, it),
                    ) {
                        popUpTo<AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail> {
                            inclusive = false
                        }
                    }
                },
                navigateToMakeNotice = {
                    navController.navigate(
                        AppNavigation.Screen.Studies.MakeNotice(groupId),
                    ) {
                        popUpTo<AppNavigation.Screen.Studies.MakeNotice> {
                            inclusive = false
                        }
                    }
                },
                navigateToMakeVote = {
                    navController.navigate(
                        AppNavigation.Screen.Studies.MakeVote(groupId),
                    ) {
                        popUpTo<AppNavigation.Screen.Studies.MakeVote> {
                            inclusive = false
                        }
                    }
                },
            )
        }

        composable<AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail> {
            NoticeDetailRoute(
                backStackEntry = it,
                viewModel = hiltViewModel(it),
            ) {
                navController.popBackStack()
            }
        }

        composable<AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail> {
            VoteDetailRoute(
                backStackEntry = it,
                viewModel = hiltViewModel(it),
                navigateToVotedPersonList = { title, votedPersonList ->
                    val json = Json.encodeToString(votedPersonList)
                    navController.navigate(
                        AppNavigation.Screen.Studies.SubTab.Screen.VotedPerson(title, json),
                    )
                },
            ) {
                navController.popBackStack()
            }
        }

        composable<AppNavigation.Screen.Studies.SubTab.Screen.VotedPerson> { backStackEntry ->
            val (title, votedPersonList) =
                backStackEntry.toRoute<AppNavigation.Screen.Studies.SubTab.Screen.VotedPerson>()
                    .let {
                        Pair(
                            it.title,
                            it.votedPersonList.let { jsonPersonList ->
                                Json.decodeFromString<List<StudyGroupVoteStatusInfo.VotedMemberInfo>>(
                                    jsonPersonList,
                                )
                            },
                        )
                    }
            VotedPersonListRoute(
                title,
                votedPersonList,
            ) {
                navController.popBackStack()
            }
        }
    }
}
