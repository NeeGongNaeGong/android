package com.ssafy.neegongnaegong.presentation.calendar

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.usecase.calendar.CreatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.DeletePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetUserSchedulesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getUserSchedulesUseCase: GetUserSchedulesUseCase,
    private val createPersonalSchedulesUseCase: CreatePersonalSchedulesUseCase,
    private val deletePersonalSchedulesUseCase: DeletePersonalSchedulesUseCase,
) : BaseViewModel<CalendarContract.Event, CalendarContract.State, CalendarContract.Effect>() {
    override fun createInitialState(): CalendarContract.State = CalendarContract.State()

    override fun handleEvent(event: CalendarContract.Event) {
        when (event) {
            is CalendarContract.Event.OnMonthSelected -> getSchedules(event.month)

            is CalendarContract.Event.OnDateSelected -> setSelectedDate(event.date)

            is CalendarContract.Event.OnCreateScheduleClicked -> createSchedule(
                event.date,
                event.title
            )

            is CalendarContract.Event.OnDeleteScheduleClicked -> deleteSchedule(
                event.id,
                event.type,
                event.date
            )

            is CalendarContract.Event.OnEditScheduleClicked -> setEffect {
                CalendarContract.Effect.NavigateToScheduleDetailScreen(event.schedule)
            }

            is CalendarContract.Event.OnScheduleClicked -> setEffect {
                CalendarContract.Effect.NavigateToScheduleDetailScreen(event.schedule)
            }

            CalendarContract.Event.OnDialogDismissed -> {
                setState { copy(isCalendarDialogShow = false) }
            }
        }
    }

    private fun getSchedules(month: YearMonth) = viewModelScope.launch {
        getUserSchedulesUseCase(month).safeCollect { result ->
            val schedules = uiState.value.schedules.toMutableMap().apply {
                result.forEach { schedule ->
                    val date = schedule.info.startDate.toLocalDate()
                    put(
                        date,
                        getOrDefault(date, emptyList()).toMutableList().apply { add(schedule) })
                }
            }
            setState { copy(schedules = schedules) }
        }
    }

    private fun createSchedule(date: LocalDate, title: String) = viewModelScope.launch {
        if (title.isBlank()) {
            CalendarContract.Effect.NavigateToCreateScheduleScreen(date)
        } else {
            createPersonalSchedulesUseCase(
                ScheduleInfo(
                    title = title,
                    content = "",
                    startDate = LocalDateTime.of(date, LocalTime.MIN),
                    endDate = LocalDateTime.of(date, LocalTime.MAX),
                    location = null,
                    repeatRule = null
                )
            )
        }
    }

    private fun deleteSchedule(id: Long, type: DeleteType, date: LocalDate) =
        viewModelScope.launch {
            deletePersonalSchedulesUseCase(id, type, date).safeCollect()
        }

    private fun setSelectedDate(date: LocalDate) {
        setState {
            copy(
                selectedDate = date,
                isCalendarDialogShow = uiState.value.schedules[selectedDate]?.isNotEmpty() ?: false
            )
        }
    }

    private suspend fun <T> Flow<T>.safeCollect(block: suspend (T) -> Unit = {}) {
        runCatching {
            collect { value -> block(value) }
        }.onFailure { error ->
            error.printStackTrace()
            setState { copy(isFailure = true) }
            setEffect {
                CalendarContract.Effect.ShowErrorSnackBar(error.message ?: "에러 발생",)
            }
        }
    }
}
