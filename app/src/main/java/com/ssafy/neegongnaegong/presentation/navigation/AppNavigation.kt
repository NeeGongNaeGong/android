package com.ssafy.neegongnaegong.presentation.navigation

import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/*
 추가 하는 방법
    ★인자 추가하는 법 초간단★
    그냥 Class 내에 인자로 설정하면 됨 끝
 */

object AppNavigation {
    /**
     * 로그인 화면 화면 경로
     */
    @Serializable
    data object Login

    /**
     * Bottom Tab으로 쓸 각 탭과 탭의 경로를 설정
     */
    @Serializable
    sealed interface Tab {
        // 각각 Studies Tab, Personal Tab, Calendar Tab, Profile Tab 생성
        // 이런 식으로 이제는 각 NavGraph의 navigation의 route를 제네릭 타입으로 AppNavigation에서 Tab 내부에 구현한 클래스를 건네주면 됨
        @Serializable
        data object Studies : Tab

        @Serializable
        data object Personal : Tab

        @Serializable
        data object Calendar : Tab

        @Serializable
        data object Profile : Tab
    }

    /**
     * 각 Tab별로 내부 화면 내비게이션 정리
     */
    @Serializable
    sealed interface Screen {
        // Study Tab에 들어갈 화면들을 선언
        @Serializable
        sealed interface Studies : Screen {
            // 여기에 Studies 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            @Serializable
            data object Main : Studies

            @Serializable
            data object MakeVote : Studies
        }

        @Serializable
        sealed interface Personal : Screen {
            // 여기에 Personal 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            @Serializable
            data object Main : Personal
        }

        @Serializable
        sealed interface Calendar : Screen {
            @Serializable
            data object Main : Calendar

            @Serializable
            data class Create(val date: String) : Calendar {
                constructor(date: LocalDate) : this(DateTimeFormatter.ISO_LOCAL_DATE.format(date))
                fun date(): LocalDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
            }

            @Serializable
            data class Detail(val scheduleId: Long) : Calendar

            @Serializable
            data class Edit(val scheduleId: Long) : Calendar
        }

        @Serializable
        sealed interface Profile : Screen {
            // 여기에 Profile 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            @Serializable
            data class Main(val userId: Int = -1) : Profile
        }
    }
}
