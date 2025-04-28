package com.ssafy.neegongnaegong.presentation.calendar.create

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.usecase.calendar.CreatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ScheduleCreateViewModel @Inject constructor(
    private val createPersonalSchedulesUseCase: CreatePersonalSchedulesUseCase,
) : BaseViewModel<ScheduleCreateContract.Event, ScheduleCreateContract.State, ScheduleCreateContract.Effect>() {

    override fun createInitialState(): ScheduleCreateContract.State = ScheduleCreateContract.State()

    override fun handleEvent(event: ScheduleCreateContract.Event) {
        when (event) {
            is ScheduleCreateContract.Event.OnLoad -> onLoad(event.date)
            is ScheduleCreateContract.Event.OnCreateScheduleClicked -> createSchedule()
            is ScheduleCreateContract.Event.OnTitleChanged -> setSchedule(title = event.title)
            is ScheduleCreateContract.Event.OnContentChanged -> setSchedule(content = event.content)
            is ScheduleCreateContract.Event.OnStartDateChanged -> setSchedule(startDate = event.date)
            is ScheduleCreateContract.Event.OnEndDateChanged -> setSchedule(endDate = event.date)
            is ScheduleCreateContract.Event.OnLocationChanged -> setSchedule(location = event.location)
            is ScheduleCreateContract.Event.OnRepeatRuleChanged -> setSchedule(repeatRule = event.repeatRule)
            is ScheduleCreateContract.Event.OnCancelClick -> setEffect { ScheduleCreateContract.Effect.NavigateBack }
        }
    }

    private fun onLoad(date: LocalDate) {
        setSchedule(
            startDate = date.atStartOfDay(),
            endDate = date.atStartOfDay().plusDays(1).minusSeconds(1)
        )
    }

    private fun setSchedule(
        title: String = uiState.value.schedule.title,
        content: String? = uiState.value.schedule.content,
        startDate: LocalDateTime = uiState.value.schedule.startAt,
        endDate: LocalDateTime = uiState.value.schedule.endAt,
        location: String? = uiState.value.schedule.location,
        repeatRule: RepeatRuleInfo? = uiState.value.repeatRule,
    ) {
        setState {
            copy(
                schedule = ScheduleInfo(
                    title = title,
                    content = content,
                    startAt = startDate,
                    endAt = endDate,
                    location = location,
                    isAllDay = startDate.toLocalTime() == LocalTime.MIN && endDate.toLocalTime() == LocalTime.MAX,
                ),
                repeatRule = repeatRule
            )
        }
    }

    private fun createSchedule() = viewModelScope.launch {
        with(uiState.value) {
            createPersonalSchedulesUseCase(
                schedule = schedule,
                repeatRule = repeatRule,
            ).withLoading {
                setState { copy(isOnCreate = it) }
            }.safeCollect {
                setEffect { ScheduleCreateContract.Effect.NavigateBack }
            }
        }
    }
}
