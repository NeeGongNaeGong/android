package com.ssafy.neegongnaegong.presentation.group

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class StudiesContract {
    sealed class Event : UiEvent {
        data object LoadGroups : Event()

        data class StudiesClicked(
            val studiesId: Long,
        ) : Event()
    }

    data class State(
        val studiesState: StudiesState,
    ) : UiState

    sealed class StudiesState {
        data object Idle : StudiesState()

        data object Loading : StudiesState()

        data class Success(
            val studiesList: List<Studies>,
        ) : StudiesState()

        data class Error(
            val message: String,
        ) : StudiesState()
    }

    sealed class Effect : UiEffect {
        data object ShowStudies : Effect()

        data class NavigateToGroupDetail(
            val studiesId: Long,
        ) : Effect()
    }
}
