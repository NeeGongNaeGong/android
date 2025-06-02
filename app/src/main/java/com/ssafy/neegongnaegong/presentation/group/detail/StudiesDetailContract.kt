package com.ssafy.neegongnaegong.presentation.group.detail

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class StudiesDetailContract {
    sealed interface Event : UiEvent {
        data class OnLoad(
            val studyGroupId: Long,
        ) : Event

        data class OndDeleteStudies(
            val studyGroupId: Long,
        ) : Event
    }

    data class State(
        val isLoading: Boolean = false,
        val studies: Studies = Studies.empty(),
    ) : UiState

    sealed interface Effect : UiEffect

    sealed interface Error : ErrorContext
}
