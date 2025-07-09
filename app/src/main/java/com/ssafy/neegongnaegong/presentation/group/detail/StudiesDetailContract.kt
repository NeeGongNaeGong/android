package com.ssafy.neegongnaegong.presentation.group.detail

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContent
import com.ssafy.neegongnaegong.domain.model.studies.WeeklyRankingsMember
import com.ssafy.neegongnaegong.domain.model.studygroup.Role
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupDetailInfo
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import java.time.LocalDate
import java.time.LocalDateTime

class StudiesDetailContract {
    sealed interface Event : UiEvent {
        data class OnLoad(
            val studyGroupId: Long,
        ) : Event

        data class OnLoadFeeds(
            val studyGroupId: Long,
        ) : Event

        data class OnLoadWeeklyRankings(
            val studyGroupId: Long,
        ) : Event

        data class OnLoadLatestContents(
            val studyGroupId: Long,
        ) : Event

        data class OndDeleteStudies(
            val studyGroupId: Long,
        ) : Event

        data class OnClickLatestNotice(
            val noticeId: Long,
        ) : Event

        data class OnClickLatestVote(
            val voteId: Long,
        ) : Event

        data class OnClickContents(
            val startTabIndex: Int,
        ) : Event
    }

    data class State(
        val isLoading: Boolean = false,
        val studyGroupDetailInfo: StudyGroupDetailInfo =
            StudyGroupDetailInfo(
                id = 8666,
                name = "올바른 접근이 아닙니다",
                description = "올바른 접근이 아닙니다",
                profileImg = "",
                isPublic = false,
                maxMembers = 1,
                currentMembers = 1,
                targetStudyTime = 1,
                category =
                    StudyGroupDetailInfo.Category(
                        id = -1,
                        name = "",
                    ),
                leaderId = -1,
                leaderName = "",
                createdDate = LocalDate.now(),
                tags = listOf(),
                myGroupRole = Role.PENDING,
            ),
        // feed
        val feeds: List<LearningRecord> = emptyList(),
        val feedsHasNext: Boolean = true,
        val feedsCursorCreatedAt: LocalDateTime? = null,
        val feedsCursorId: Long? = null,
        // weekly-rankings
        val weeklyRankings: List<WeeklyRankingsMember> = emptyList(),
        val weeklyRankingsHasNext: Boolean = true,
        val weeklyRankingsCursorStudyTime: Long? = null,
        val weeklyRankingsCursorUserId: Long? = null,
        val weeklyRankingsFirstPageRequestedAt: LocalDateTime? = null,
        // latest-contents
        val latestNotice: StudiesLatestContent.LatestNotice? = null,
        val latestNoticeReadChecked: Boolean = false,
        val latestVote: StudiesLatestContent.LatestVote? = null,
        val latestVoteReadChecked: Boolean = false,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToLatestNoticeDetail(
            val noticeId: Long,
        ) : Effect

        data class NavigateToLatestVoteDetail(
            val voteId: Long,
        ) : Effect

        data class NavigateToContents(
            val startTabIndex: Int,
        ) : Effect
    }

    sealed interface Error : ErrorContext
}
