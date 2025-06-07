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
     * Bottom Tab으로 쓸 각 탭과 탭의 경로를 설정
     */
    @Serializable
    sealed interface Tab {
        // 각각 Studies Tab, Personal Tab, Calendar Tab, Profile Tab 생성
        // 이런 식으로 이제는 각 NavGraph의 navigation의 route를 제네릭 타입으로 AppNavigation에서 Tab 내부에 구현한 클래스를 건네주면 됨
        //

        // 생각해보면 회원가입 페이지도 있을텐데, 로그인, 회원가입들을 관리하는 Tab 하나 정도 있어야 하지 않을까 싶어서 생성
        @Serializable
        data object Auth : Tab

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
        // 회원가입과 로그인 관련 Screen들을 관리할 Auth Screen
        @Serializable
        sealed interface Auth : Screen {
            /**
             * 로그인 화면 화면 경로
             */
            @Serializable
            data object Login : Auth
        }

        // Study Tab에 들어갈 화면들을 선언
        @Serializable
        sealed interface Studies : Screen {
            // 여기에 Studies 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            @Serializable
            data object Main : Studies

            @Serializable
            data class StudiesDetail(val studyGroupId: Long) : Studies

            @Serializable
            data class StudiesApplication(val studyGroupId: Long) : Studies

            @Serializable
            data object Management : Studies

            @Serializable
            data class MakeVote(val studyGroupId: Int) : Studies

            @Serializable
            data class Record(val groupId: Long, val memberId: Long) : Studies
        }

        @Serializable
        sealed interface Personal : Screen {
            // 여기에 Personal 탭에 있는 각 화면들 경로 등록하면 됩니당
            // Study Tab의 Main 화면의 경로
            @Serializable
            data object Main : Personal

            @Serializable
            data class Edit(val studyRecordId: Long) : Personal
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
            data class Main(
                val userId: Int = -1,
            ) : Profile
        }
    }
}
