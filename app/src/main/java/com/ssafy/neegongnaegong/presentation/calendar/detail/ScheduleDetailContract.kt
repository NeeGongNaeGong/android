package com.ssafy.neegongnaegong.presentation.calendar.detail

import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.calendar.component.form.ScheduleInputFormFocus
import java.time.LocalDate

class ScheduleDetailContract {
    sealed class Event : UiEvent {
        data class OnLoad(val scheduleId: Long, val date: LocalDate) : Event()

        data object OnEditClick : Event()

        data class OnFormClick(val focus: ScheduleInputFormFocus) : Event()

        data object OnDeleteClick : Event()

        data class OnDeleteTypeSelected(val type: DeleteType) : Event()

        data object OnDialogDismissed : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isFailure: Boolean = false,
        val isOnDelete: Boolean = false,
        val isDeleteTypeSelectorShow: Boolean = false,
        val schedule: Schedule = Schedule.empty(),
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()

        data class NavigateToEditScheduleScreen(
            val schedule: Schedule,
            val focus: ScheduleInputFormFocus,
        ) : Effect()
    }
}
