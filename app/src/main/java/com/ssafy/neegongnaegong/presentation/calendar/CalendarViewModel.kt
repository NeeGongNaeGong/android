package com.ssafy.neegongnaegong.presentation.calendar

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.exception.ApiException
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.usecase.calendar.CreatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.DeletePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetUserSchedulesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel
    @Inject
    constructor(
        private val getUserSchedulesUseCase: GetUserSchedulesUseCase,
        private val createPersonalSchedulesUseCase: CreatePersonalSchedulesUseCase,
        private val deletePersonalSchedulesUseCase: DeletePersonalSchedulesUseCase,
    ) : BaseViewModel<CalendarContract.Event, CalendarContract.State, CalendarContract.Effect>() {
        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            // 너무 많은 수정이 필요할 예정이라 당장은 타입을 제너릭으로 받지 않았습니다.
            val error = errorContext as? CalendarContract.Error ?: return
            when (e) {
                is ApiException.NetworkException -> {
                }
            }
            when (error) {
                is CalendarContract.Error.GetSchedulesError ->
                    showErrorMessage(
                        "스캐줄 정보를 가져오지 못했습니다.",
                        SnackbarManager.Action.retry { retry() },
                    )

                is CalendarContract.Error.CreateScheduleError ->
                    showErrorMessage(
                        "스캐줄 생성에 실패하였습니다.",
                        SnackbarManager.Action.retry { retry() },
                    )
            }
        }

        override fun createInitialState(): CalendarContract.State = CalendarContract.State()

        override fun handleEvent(event: CalendarContract.Event) {
            when (event) {
                is CalendarContract.Event.OnMonthSelected -> getSchedules(event.month)

                is CalendarContract.Event.OnDateSelected -> setSelectedDate(event.date)

                is CalendarContract.Event.OnCreateScheduleClicked ->
                    createSchedule(
                        event.date,
                        event.title,
                    )

                is CalendarContract.Event.OnDeleteScheduleClicked ->
                    deleteSchedule(
                        event.id,
                        event.type,
                        event.date,
                    )

                is CalendarContract.Event.OnEditScheduleClicked ->
                    setEffect {
                        CalendarContract.Effect.NavigateToScheduleEditScreen(event.schedule)
                    }

                is CalendarContract.Event.OnScheduleClicked ->
                    setEffect {
                        CalendarContract.Effect.NavigateToScheduleDetailScreen(event.schedule)
                    }

                CalendarContract.Event.OnDialogDismissed ->
                    setState {
                        copy(isCalendarDialogShow = false)
                    }
            }
        }

        private fun getSchedules(month: YearMonth) =
            viewModelScope.launch {
                getUserSchedulesUseCase(month).withLoading {
                    setState { copy(isLoading = it) }
                }.safeCollect(CalendarContract.Error.GetSchedulesError) { result ->
                    val schedules =
                        mutableMapOf<LocalDate, MutableList<Schedule>>().apply {
                            result.forEach { schedule ->
                                val date = schedule.info.startAt.toLocalDate()
                                put(
                                    date,
                                    getOrDefault(date, emptyList()).toMutableList().apply { add(schedule) },
                                )
                            }
                        }
                    setState { copy(schedules = schedules) }
                }
            }

        private fun createSchedule(
            date: LocalDate,
            title: String,
        ) = viewModelScope.launch {
            if (title.isBlank()) {
                setEffect { CalendarContract.Effect.NavigateToCreateScheduleScreen(date) }
            } else {
                createPersonalSchedulesUseCase(
                    ScheduleInfo(
                        title = title,
                        content = "",
                        startAt = LocalDateTime.of(date, LocalTime.MIN),
                        endAt = LocalDateTime.of(date, LocalTime.MAX),
                        isAllDay = true,
                        location = null,
                        repeatRule = null,
                    ),
                ).withLoading {
                    setState { copy(isOnCreate = it) }
                }.safeCollect(CalendarContract.Error.CreateScheduleError) {
                    getSchedules(YearMonth.from(date))
                }
            }
        }

        private fun deleteSchedule(
            id: Long,
            type: DeleteType,
            date: LocalDate,
        ) = viewModelScope.launch {
            deletePersonalSchedulesUseCase(id, type, date).safeCollect()
        }

        private fun setSelectedDate(date: LocalDate) {
            setState {
                copy(
                    selectedDate = date,
                    isCalendarDialogShow = uiState.value.schedules[date]?.isNotEmpty() ?: false,
                )
            }
        }
    }
