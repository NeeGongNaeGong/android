package com.ssafy.neegongnaegong.presentation.calendar

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() :
    BaseViewModel<CalendarContract.Event, CalendarContract.State, CalendarContract.Effect>() {
    override fun createInitialState(): CalendarContract.State = CalendarContract.State()

    override fun handleEvent(event: CalendarContract.Event) {
        when (event) {
            is CalendarContract.Event.OnDateSelected -> {
                setState {
                    copy(
                        selectedDate = event.date,
                        isCalendarDialogShow = uiState.value.schedules[selectedDate]?.isNotEmpty() ?: false
                    )
                }
            }

            is CalendarContract.Event.OnCreateScheduleClicked -> {
                if (event.title.isBlank()) {
                    CalendarContract.Effect.NavigateToCreateScheduleScreen(event.date)
                } else {
                    createSchedule(date = event.date, title = event.title)
                }
            }

            is CalendarContract.Event.OnDeleteScheduleClicked -> {
                deleteSchedule(event.id)
            }

            is CalendarContract.Event.OnEditScheduleClicked -> {
                CalendarContract.Effect.NavigateToScheduleDetailScreen(event.schedule)
            }

            is CalendarContract.Event.OnScheduleClicked -> {
                setEffect {
                    CalendarContract.Effect.NavigateToScheduleDetailScreen(event.schedule)
                }
            }

            CalendarContract.Event.OnDialogDismissed -> {
                setState { copy(isCalendarDialogShow = false) }
            }
        }
    }

    private fun createSchedule(date: LocalDate, title: String) = viewModelScope.launch {

    }

    private fun deleteSchedule(id: Long) = viewModelScope.launch {

    }
}
