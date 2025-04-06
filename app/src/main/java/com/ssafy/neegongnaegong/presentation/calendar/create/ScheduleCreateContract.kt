package com.ssafy.neegongnaegong.presentation.calendar.create

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleCreateContract {
    sealed class Event : UiEvent {
        data class OnLoad(val date: LocalDate) : Event()
        data class OnTitleChanged(val title: String) : Event()
        data class OnContentChanged(val content: String?) : Event()
        data class OnStartDateChanged(val date: LocalDateTime) : Event()
        data class OnEndDateChanged(val date: LocalDateTime) : Event()
        data class OnIsAllDayChanged(val isAllDay: Boolean) : Event()
        data class OnLocationChanged(val location: String?) : Event()
        data class OnRepeatRuleChanged(val repeatRule: RepeatRuleInfo?) : Event()
        data object OnCreateScheduleClicked : Event()
        data object OnCancelClick: Event()
    }

    data class State(
        val isSuccess: Boolean = false,
        val isFailure: Boolean = false,
        val isOnCreate: Boolean = false,
        val schedule: ScheduleInfo? = null,
        val repeatRule: RepeatRuleInfo? = null,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data class ShowErrorSnackBar(val message: String) : Effect()
    }
}
