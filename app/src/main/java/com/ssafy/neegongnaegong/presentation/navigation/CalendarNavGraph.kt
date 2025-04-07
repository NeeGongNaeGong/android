package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ssafy.neegongnaegong.presentation.CalendarScreen

fun NavGraphBuilder.calendarNavGraph(navController: NavController){
    // 이런 식으로 이제는 navigation의 route를 제네릭 타입으로 AppNavigation에서 Tab 내부에 구현한 클래스를 건네줌
    navigation<AppNavigation.Tab.Calendar>(
        startDestination = AppNavigation.Screen.Calendar.Main,
    ){
        composable<AppNavigation.Screen.Calendar.Main> {
            CalendarScreen()
        }
    }
}
