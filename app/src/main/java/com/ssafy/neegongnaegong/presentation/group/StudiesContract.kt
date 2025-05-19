package com.ssafy.neegongnaegong.presentation.group

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class StudiesContract {
    sealed class Event : UiEvent {
        data object OnLoadStudies : Event()

        data class StudiesClicked(
            val studiesId: Long,
        ) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val studiesList: List<Studies> = emptyList(),
        val hasNext: Boolean = true,
        val cursorCreateAt: String? = null,
        val cursorId: Long? = null,
    ) : UiState

    sealed class Effect : UiEffect {
        data object ShowStudies : Effect()

        data class NavigateToGroupDetail(
            val studiesId: Long,
        ) : Effect()
    }

    sealed class Error : ErrorContext {
        data object GetStudiesListError : Error()
    }
}
