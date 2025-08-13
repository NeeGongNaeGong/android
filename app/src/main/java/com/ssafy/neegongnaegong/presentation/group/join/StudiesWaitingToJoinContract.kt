package com.ssafy.neegongnaegong.presentation.group.join

import com.ssafy.neegongnaegong.domain.model.studies.StudiesApplicationsMember
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class StudiesWaitingToJoinContract {
    sealed interface Event : UiEvent {
        data class OnLoadStudiesApplications(
            val studyGroupId: Long,
        ) : Event

        data class OnApproval(
            val userId: Long,
        ) : Event

        data class OnReject(
            val userId: Long,
        ) : Event
    }

    data class State(
        val isLoading: Boolean = false,
        val studyGroupId: Long,
        val applicationsList: List<StudiesApplicationsMember> = emptyList(),
        val hasNext: Boolean = true,
        val cursorId: Long? = null,
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToStudiesDetail : Effect
    }

    sealed interface Error : ErrorContext
}
