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

object AppNavigation {
    /**
     * 각 탭마다 쓸 경로 설정
     */
    private object TabRoutes {
        // 각각 Studies Tab, Personal Tab, Calendar Tab, Profile Tab 및 그 아래의 화면에서 쓸 경로 지정
        const val STUDIES = "studies"
        const val PERSONAL = "personal"
        const val CALENDAR = "calendar"
        const val PROFILE = "profile"
    }

    /**
     * Bottom Tab으로 쓸 각 탭과 탭의 경로를 설정
     */
    sealed class Tab(val route: String) {
        // 각각 Studies Tab, Personal Tab, Calendar Tab, Profile Tab 생성
        data object Studies : Tab(TabRoutes.STUDIES)
        data object Personal : Tab(TabRoutes.PERSONAL)
        data object Calendar : Tab(TabRoutes.CALENDAR)
        data object Profile : Tab(TabRoutes.PROFILE) {
            fun createRoute(userId: Int): String = "${route}/$userId"
        }
    }

    /**
     * 각 Tab별로 내부 화면 내비게이션 정리
     */
    sealed class Screen(open val route: String) {
        // Study Tab에 들어갈 화면들을 선언
        sealed class Studies(override val route: String = TabRoutes.STUDIES) : Screen(route) {
            // 여기에 Studies 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            data object Main : Studies("${Tab.Studies.route}/main")
        }

        sealed class Personal(override val route: String) : Screen(route) {
            // 여기에 Personal 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            data object Main : Personal("${Tab.Personal.route}/main")
        }

        sealed class Calendar(override val route: String) : Screen(route) {
            // 여기에 Calendar 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            data object Main : Calendar("${Tab.Calendar.route}/main")
        }

        sealed class Profile(override val route: String) : Screen(route) {
            // 여기에 Profile 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            data object Main : Profile("${Tab.Profile.createRoute(-1)}/main")
        }
    }
}
