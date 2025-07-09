package com.ssafy.neegongnaegong.presentation.navigation

import VotedPersonListRoute
import android.content.Intent
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.BuildConfig
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteStatusInfo
import com.ssafy.neegongnaegong.presentation.group.StudiesRoute
import com.ssafy.neegongnaegong.presentation.group.create.StudiesCreateRoute
import com.ssafy.neegongnaegong.presentation.group.detail.StudiesDetailRoute
import com.ssafy.neegongnaegong.presentation.group.edit.StudiesEditRoute
import com.ssafy.neegongnaegong.presentation.group.join.StudiesWaitingToJoinRoute
import com.ssafy.neegongnaegong.presentation.group.list.main.ListRoute
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailRoute
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailRoute
import com.ssafy.neegongnaegong.presentation.group.notice.NoticeRoute
import com.ssafy.neegongnaegong.presentation.group.record.RecordRoute
import com.ssafy.neegongnaegong.presentation.group.role.StudiesMemberRoleRoute
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
            StudiesRoute(
                modifier = Modifier,
                popBackStack = { },
                navigateToStudiesDetail = {
                    navController.navigate(AppNavigation.Screen.Studies.StudiesDetail(it))
                },
                navigateToStudiesManagement = {
                    navController.navigate(AppNavigation.Screen.Studies.Create)
                },
            )
        }

        navigation<AppNavigation.Tab.StudiesDetail>(
            startDestination = AppNavigation.Screen.Studies.StudiesDetail(-1),
        ) {
            composable<AppNavigation.Screen.Studies.StudiesDetail>(
                deepLinks =
                    listOf(
                        navDeepLink {
                            uriPattern = "$BASE_DEEP_LINK/study-group/{studyGroupId}"
                            action = Intent.ACTION_VIEW
                        },
                    ),
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<AppNavigation.Screen.Studies.StudiesDetail>()
                StudiesDetailRoute(
                    modifier = Modifier,
                    navBackStackEntry = backStackEntry,
                    studyGroupId = route.studyGroupId,
                    navigateToContents = { startTabIndex, studyGroupId ->
                        navController.navigate(
                            AppNavigation.Screen.Studies.SubTab.Main(
                                startTabIndex,
                                studyGroupId,
                            ),
                        )
                    },
                    navigateToLatestNoticeDetail = { studyGroupId, noticeId ->
                        navController.navigate(
                            AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail(
                                studyGroupId,
                                noticeId,
                            ),
                        )
                    },
                    navigateToLatestVoteDetail = { studyGroupId, voteId ->
                        navController.navigate(
                            AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail(
                                studyGroupId,
                                voteId,
                            ),
                        )
                    },
                    popBackStack = navController::popBackStack,
                )
            }

            composable<AppNavigation.Screen.Studies.StudiesApplication>(
                deepLinks =
                    listOf(
                        navDeepLink {
                            uriPattern = "$BASE_DEEP_LINK/study-group/{studyGroupId}/manage/{role}"
                            action = Intent.ACTION_VIEW
                        },
                    ),
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<AppNavigation.Screen.Studies.StudiesApplication>()
                StudiesWaitingToJoinRoute(
                    modifier = Modifier,
                    studyGroupId = route.studyGroupId,
                    role = route.role,
                    popBackStack = navController::popBackStack,
                )
            }

            composable<AppNavigation.Screen.Studies.StudiesMembersRole> { backStackEntry ->
                val route = backStackEntry.toRoute<AppNavigation.Screen.Studies.StudiesMembersRole>()
                StudiesMemberRoleRoute(
                    modifier = Modifier,
                    studyGroupId = route.studyGroupId,
                    role = route.role,
                    popBackStack = navController::popBackStack,
                )
            }

            composable<AppNavigation.Screen.Studies.Create> {
                StudiesCreateRoute(
                    modifier = Modifier,
                    popBackStack = navController::popBackStack,
                )
            }

            composable<AppNavigation.Screen.Studies.Edit> { backStackEntry ->
                val route = backStackEntry.toRoute<AppNavigation.Screen.Studies.Edit>()
                StudiesEditRoute(
                    modifier = Modifier,
                    studyGroupId = route.studyGroupId,
                    role = route.role,
                    popBackStack = navController::popBackStack,
                )
            }

            composable<AppNavigation.Screen.Studies.MakeVote> {
                VoteRoute(
                    navigateToMain = { startTab, studyGroupId ->
                        navController.navigate(
                            AppNavigation.Screen.Studies.SubTab.Main(
                                startTab,
                                studyGroupId,
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
                    popBackStackInclusive = { startTab, studyGroupId ->
                        navController.navigate(
                            AppNavigation.Screen.Studies.SubTab.Main(
                                startTab,
                                studyGroupId,
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
                val (studyGroupId, memberId) = backStackEntry.toRoute<AppNavigation.Screen.Studies.Record>()
                RecordRoute(
                    studyGroupId = studyGroupId,
                    memberId = memberId,
                    popBackStack = navController::popBackStack,
                )
            }

            navigation<AppNavigation.Tab.StudiesSubTabDetail>(
                startDestination = AppNavigation.Screen.Studies.SubTab.Main(0, -1),
            ) {
                composable<AppNavigation.Screen.Studies.SubTab.Main> { backStackEntry ->
                    val (startTabIndex, studyGroupId) = backStackEntry.toRoute<AppNavigation.Screen.Studies.SubTab.Main>()
                    ListRoute(
                        popBackStack = navController::popBackStack,
                        startTabIdx = startTabIndex,
                        navigateToNoticeDetail = {
                            navController.navigate(
                                AppNavigation.Screen.Studies.SubTab.Screen
                                    .NoticeDetail(studyGroupId, it),
                            ) {
                                popUpTo<AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail> {
                                    inclusive = false
                                }
                            }
                        },
                        navigateToVoteDetail = {
                            navController.navigate(
                                AppNavigation.Screen.Studies.SubTab.Screen
                                    .VoteDetail(studyGroupId, it),
                            ) {
                                popUpTo<AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail> {
                                    inclusive = false
                                }
                            }
                        },
                        navigateToMakeNotice = {
                            navController.navigate(
                                AppNavigation.Screen.Studies.MakeNotice(studyGroupId),
                            ) {
                                popUpTo<AppNavigation.Screen.Studies.MakeNotice> {
                                    inclusive = false
                                }
                            }
                        },
                        navigateToMakeVote = {
                            navController.navigate(
                                AppNavigation.Screen.Studies.MakeVote(studyGroupId),
                            ) {
                                popUpTo<AppNavigation.Screen.Studies.MakeVote> {
                                    inclusive = false
                                }
                            }
                        },
                    )
                }

                composable<AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail>(
                    deepLinks =
                        listOf(
                            navDeepLink {
                                uriPattern = "$BASE_DEEP_LINK/study-group/{studyGroupId}/notice/{noticeId}"
                                action = Intent.ACTION_VIEW
                            },
                        ),
                ) {
                    val studyGroupId =
                        it.toRoute<AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail>().studyGroupId
                    NoticeDetailRoute(
                        backStackEntry = it,
                        viewModel = hiltViewModel(it),
                        popBackStack = navController::popBackStack,
                    ) {
                        navController.navigate(
                            AppNavigation.Screen.Studies.SubTab.Main(
                                startTab = AppNavigation.Screen.Studies.SubTab.SubTabMenu.NoticeTab.index,
                                studyGroupId = studyGroupId,
                            ),
                        ) {
                            popUpTo<AppNavigation.Screen.Studies.SubTab.Main> {
                                inclusive = true
                            }
                        }
                    }
                }

                composable<AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail> (
                    deepLinks =
                        listOf(
                            navDeepLink {
                                uriPattern = "$BASE_DEEP_LINK/study-group/{studyGroupId}/vote/{voteId}"
                                action = Intent.ACTION_VIEW
                            },
                        ),
                ) {
                    val studyGroupId =
                        it.toRoute<AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail>().studyGroupId
                    VoteDetailRoute(
                        backStackEntry = it,
                        viewModel = hiltViewModel(it),
                        navigateToVotedPersonList = { title, votedPersonList ->
                            val json = Json.encodeToString(votedPersonList)
                            navController.navigate(
                                AppNavigation.Screen.Studies.SubTab.Screen
                                    .VotedPerson(title, json),
                            )
                        },
                        popBackStack = navController::popBackStack,
                    ) {
                        navController.navigate(
                            AppNavigation.Screen.Studies.SubTab.Main(
                                startTab = AppNavigation.Screen.Studies.SubTab.SubTabMenu.VoteTab.index,
                                studyGroupId = studyGroupId,
                            ),
                        ) {
                            popUpTo<AppNavigation.Screen.Studies.SubTab.Main> {
                                inclusive = true
                            }
                        }
                    }
                }

                composable<AppNavigation.Screen.Studies.SubTab.Screen.VotedPerson> { backStackEntry ->
                    val (title, votedPersonList) =
                        backStackEntry
                            .toRoute<AppNavigation.Screen.Studies.SubTab.Screen.VotedPerson>()
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
    }
}

const val BASE_DEEP_LINK: String = BuildConfig.BASE_URL
