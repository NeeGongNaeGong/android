package com.ssafy.neegongnaegong.presentation.group.detail

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContent
import com.ssafy.neegongnaegong.domain.model.studies.WeeklyRankingsMember
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
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
    }

    data class State(
        val isLoading: Boolean = false,
        val studies: Studies = Studies.empty(),
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
        val latestVote: StudiesLatestContent.LatestVote? = null,
    ) : UiState

    sealed interface Effect : UiEffect

    sealed interface Error : ErrorContext
}
