package com.ssafy.neegongnaegong.presentation.calendar.edit

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleEditContract {
    sealed class Event : UiEvent {
        data class InitSchedule(val schedule: Schedule) : Event()
        data class OnTitleChanged(val title: String) : Event()
        data class OnContentChanged(val content: String?) : Event()
        data class OnStartDateChanged(val date: LocalDateTime) : Event()
        data class OnEndDateChanged(val date: LocalDateTime) : Event()
        data class OnIsAllDayChanged(val isAllDay: Boolean) : Event()
        data class OnLocationChanged(val location: String?) : Event()
        data class OnRepeatRuleChanged(val repeatRule: RepeatRuleInfo?) : Event()
        data class OnSaveScheduleClicked(val type: UpdateType) : Event()
        data object OnCancelClick: Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isFailure: Boolean = false,
        val id: Long? = null,
        val date: LocalDate? = null,
        val schedule: ScheduleInfo? = null,
        val repeatRule: RepeatRuleInfo? = null,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateBack : Effect()
        data class ShowErrorSnackBar(val message: String) : Effect()
    }
}
