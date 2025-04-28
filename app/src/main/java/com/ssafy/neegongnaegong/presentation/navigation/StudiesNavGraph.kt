package com.ssafy.neegongnaegong.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.group.StudiesRoute
import com.ssafy.neegongnaegong.presentation.group.vote.VoteRoute

/**
 * startDestination은 여기 Graph에서 최초로 띄울 화면의 경로
 * route는 이 NavGraph를 가리키는 이름
 * navController는 나중에 네비게이트로 이동할 때 용도
 * 여기가 Study Nav Graph이지만
 * 모든 경로의 이름이 BottomNavigation.Screen.~~.route로 다 들어가 있기 때문에
 * 여기서 Profile 탭의 화면의 경로도 BottomNavigation으로 접근해서 사용할 수 있다.
 */
// Study 탭의 Navigation을 설정하는 곳
fun NavGraphBuilder.studiesNavGraph(navController: NavController) {
    navigation<AppNavigation.Tab.Studies>(
        startDestination = AppNavigation.Screen.Studies.Main,
    ) {
        composable<AppNavigation.Screen.Studies.Main> {
//            StudiesScreen()
            StudiesRoute(
                modifier = Modifier,
                popBackStack = { },
            )
        }

        composable<AppNavigation.Screen.Studies.MakeVote> {

            VoteRoute(
                popBackStack = { navController.popBackStack() }
            )
        }
    }
}
