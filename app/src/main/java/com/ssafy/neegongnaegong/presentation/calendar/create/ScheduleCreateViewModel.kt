package com.ssafy.neegongnaegong.presentation.calendar.create

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.usecase.calendar.CreatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
            is ScheduleCreateContract.Event.OnIsAllDayChanged -> setSchedule(isAllDay = event.isAllDay)
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
        startDate: LocalDateTime = uiState.value.schedule.startDate,
        isAllDay: Boolean = uiState.value.schedule.isAllDay,
        endDate: LocalDateTime = uiState.value.schedule.endDate,
        location: String? = uiState.value.schedule.location,
        repeatRule: RepeatRuleInfo? = uiState.value.repeatRule,
    ) {
        setState {
            copy(
                schedule = ScheduleInfo(
                    title = title,
                    content = content,
                    startDate = if (isAllDay) LocalDateTime.of(
                        startDate.toLocalDate(),
                        LocalTime.MIN
                    )
                    else if (startDate.isAfter(endDate)) endDate
                    else startDate,
                    endDate = if (isAllDay) LocalDateTime.of(endDate.toLocalDate(), LocalTime.MAX)
                    else if (startDate.isAfter(endDate)) startDate
                    else endDate,
                    location = location,
                    isAllDay = isAllDay,
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

    private suspend fun <T> Flow<T>.safeCollect(block: suspend (T) -> Unit = {}) {
        runCatching {
            collect { value -> block(value) }
        }.onFailure { error ->
            error.printStackTrace()
            setState { copy(isFailure = true) }
            setEffect {
                ScheduleCreateContract.Effect.ShowErrorSnackBar(error.message ?: "에러 발생")
            }
        }
    }
}
