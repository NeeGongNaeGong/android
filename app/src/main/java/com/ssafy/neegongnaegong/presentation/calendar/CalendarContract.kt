package com.ssafy.neegongnaegong.presentation.calendar

import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import java.time.LocalDate
import java.time.YearMonth

class CalendarContract {
    sealed class Event : UiEvent {
        data class OnDateSelected(val date: LocalDate) : Event()

        data class OnCreateScheduleClicked(val date: LocalDate, val title: String) : Event()
        data class OnEditScheduleClicked(val schedule: Schedule) : Event()
        data class OnDeleteScheduleClicked(val id: Long) : Event()

        data class OnScheduleClicked(val schedule: Schedule) : Event()

        data object OnDialogDismissed : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isSuccess: Boolean = false,
        val isFailure: Boolean = false,
        val isCalendarDialogShow: Boolean = false,
        val selectedMonth: YearMonth = YearMonth.now(),
        val selectedDate: LocalDate = LocalDate.now(),
        val schedules: Map<LocalDate, List<Schedule>> = emptyMap(),
    ) : UiState

    sealed class Effect : UiEffect {
        data class NavigateToCreateScheduleScreen(val date: LocalDate) : Effect()
        data class NavigateToScheduleDetailScreen(val schedule: Schedule) : Effect()
    }
}