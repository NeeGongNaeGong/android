package com.ssafy.neegongnaegong.presentation.calendar.detail

import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class ScheduleDetailContract {
    sealed class Event : UiEvent {
        data class OnLoad(val schedule: Schedule) : Event()
        data object OnEditClick : Event()
        data class OnDeleteClick(val type: DeleteType) : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isFailure: Boolean = false,
        val schedule: Schedule? = null,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data class NavigateToEditScheduleScreen(val schedule: Schedule) : Effect()
        data class ShowErrorSnackBar(val message: String) : Effect()
    }
}
