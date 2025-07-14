package com.ssafy.neegongnaegong.presentation.group

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class StudiesContract {
    sealed interface Event : UiEvent {
        data object OnLoadMyStudies : Event

        data class OnClickMyStudies(
            val studyGroupId: Long,
        ) : Event

        data object OnClickCreateStudies : Event

        data object OnClickSearchStudies : Event
    }

    data class State(
        val isLoading: Boolean = false,
        val joinedStudies: List<Studies> = emptyList(),
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToStudiesDetail(
            val studyGroupId: Long,
        ) : Effect

        data object NavigateToStudiesCreate : Effect

        data object NavigateToStudiesSearch : Effect
    }

    sealed interface Error : ErrorContext {
        data object MyStudiesLoadError : Error
    }
}
