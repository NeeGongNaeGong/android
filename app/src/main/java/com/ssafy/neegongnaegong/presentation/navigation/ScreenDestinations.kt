package com.ssafy.neegongnaegong.presentation.navigation

/*
 추가 하는 방법
    data object sample : ScreenDestination(route = "화면이름"){
        // 넘기는 인자 있어야 하는 경우
        val arguments = listOf(
            navArgument(name = "id") { type = NavType.StringType },
            navArgument(name = "number") { type = NavType.IntType },
        )
        fun createRoute(
            id : String,
            number : Int
        ) = this.route/id/$number
    }
 */

sealed class ScreenDestinations(
    open val route: String,
) {
    data object Studies : ScreenDestinations("studies_screen")

    data object Personal : ScreenDestinations("personal_screen")

    data object Calendar : ScreenDestinations("calendar_screen")

    data object Profile : ScreenDestinations("profile_screen") {
        fun createRoute(userId: Int): String = "profile_screen/$userId"
    }
}
