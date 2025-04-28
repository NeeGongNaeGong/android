package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ssafy.neegongnaegong.presentation.login.LoginRoute

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation<AppNavigation.Tab.Auth>(
        startDestination = AppNavigation.Screen.Auth.Login
    ){
        composable<AppNavigation.Screen.Auth.Login> {
            LoginRoute{
                navController.navigate(AppNavigation.Tab.Studies){
                    // 로그인 등 회원가입 페이지 싹 다 밀어버리고 Studies 탭으로 이동
                    popUpTo(AppNavigation.Tab.Auth){inclusive = true}
                }
            }
        }
    }
}
