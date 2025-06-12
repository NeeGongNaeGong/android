package com.ssafy.neegongnaegong.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.presentation.calendar.CalendarRoute
import com.ssafy.neegongnaegong.presentation.calendar.component.form.ScheduleInputFormFocus
import com.ssafy.neegongnaegong.presentation.calendar.create.ScheduleCreateRoute
import com.ssafy.neegongnaegong.presentation.calendar.detail.ScheduleDetailRoute
import com.ssafy.neegongnaegong.presentation.calendar.edit.ScheduleEditRoute

fun NavGraphBuilder.calendarNavGraph(navController: NavController) {
    navigation<AppNavigation.Tab.Calendar>(
        startDestination = AppNavigation.Screen.Calendar.Main,
    ) {
        composable<AppNavigation.Screen.Calendar.Main> {
            CalendarRoute(
                popBackStack = { navController.popBackStack() },
                navigateToScheduleDetail = {
                    navController.navigate(
                        AppNavigation.Screen.Calendar.Detail(
                            it.id,
                            it.info.startAt.toLocalDate(),
                        ),
                    )
                },
                navigateToScheduleCreate = {
                    navController.navigate(AppNavigation.Screen.Calendar.Create(it))
                },
                navigateToScheduleEdit = {
                    navController.navigate(
                        AppNavigation.Screen.Calendar.Edit(
                            it.id,
                            it.info.startAt.toLocalDate(),
                            ScheduleInputFormFocus.None,
                        ),
                    )
                },
            )
        }

        composable<AppNavigation.Screen.Calendar.Detail> { backStackEntry ->
            val route = backStackEntry.toRoute<AppNavigation.Screen.Calendar.Detail>()
            ScheduleDetailRoute(
                scheduleId = route.scheduleId,
                date = route.date(),
                popBackStack = { navController.popBackStack() },
                navigateToEditScheduleScreen = { schedule, focus ->
                    navController.navigate(
                        AppNavigation.Screen.Calendar.Edit(
                            schedule.id,
                            schedule.info.startAt.toLocalDate(),
                            focus,
                        ),
                    )
                },
            )
        }

        composable<AppNavigation.Screen.Calendar.Create> { backStackEntry ->
            val route = backStackEntry.toRoute<AppNavigation.Screen.Calendar.Create>()
            ScheduleCreateRoute(
                popBackStack = { navController.popBackStack() },
                date = route.date(),
            )
        }

        composable<AppNavigation.Screen.Calendar.Edit> { backStackEntry ->
            val route = backStackEntry.toRoute<AppNavigation.Screen.Calendar.Edit>()
            ScheduleEditRoute(
                popBackStack = { navController.popBackStack() },
                scheduleId = route.scheduleId,
                date = route.date(),
                initialFocus = route.focus,
            )
        }
    }
}
