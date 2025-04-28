package com.ssafy.neegongnaegong.presentation.calendar.edit

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetScheduleDetailUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.UpdatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    private val getScheduleDetailUseCase: GetScheduleDetailUseCase,
    private val updatePersonalSchedulesUseCase: UpdatePersonalSchedulesUseCase,
) : BaseViewModel<ScheduleEditContract.Event, ScheduleEditContract.State, ScheduleEditContract.Effect>() {

    override fun createInitialState(): ScheduleEditContract.State = ScheduleEditContract.State()

    override fun handleEvent(event: ScheduleEditContract.Event) {
        when (event) {
            is ScheduleEditContract.Event.OnLoad -> onLoad(event.scheduleId)
            is ScheduleEditContract.Event.OnSaveScheduleClicked -> saveSchedule(event.type)
            is ScheduleEditContract.Event.OnTitleChanged -> setSchedule(title = event.title)
            is ScheduleEditContract.Event.OnContentChanged -> setSchedule(content = event.content)
            is ScheduleEditContract.Event.OnStartDateChanged -> setSchedule(startDate = event.date)
            is ScheduleEditContract.Event.OnEndDateChanged -> setSchedule(endDate = event.date)
            is ScheduleEditContract.Event.OnLocationChanged -> setSchedule(location = event.location)
            is ScheduleEditContract.Event.OnRepeatRuleChanged -> setSchedule(repeatRule = event.repeatRule)
            is ScheduleEditContract.Event.OnCancelClick -> setEffect { ScheduleEditContract.Effect.NavigateBack }
        }
    }

    private fun onLoad(scheduleId: Long) = viewModelScope.launch {
        getScheduleDetailUseCase(scheduleId).withLoading {
            setState { copy(isLoading = it) }
        }.safeCollect { schedule ->
            setState {
                copy(
                    id = schedule.id,
                    date = schedule.info.startAt.toLocalDate(),
                    schedule = schedule.info,
                    repeatRule = schedule.info.repeatRule?.info,
                )
            }
        }
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

    private fun saveSchedule(type: UpdateType) = viewModelScope.launch {
        with(uiState.value) {
            if (id == null) {
                setEffect { ScheduleEditContract.Effect.ShowErrorSnackBar("데이터가 회손되었습니다.") }
            } else {
                updatePersonalSchedulesUseCase(
                    id = id,
                    schedule = schedule,
                    repeatRule = repeatRule,
                    type = type,
                    date = date,
                ).withLoading {
                    setState { copy(isOnSave = it) }
                }.safeCollect {
                    setEffect { ScheduleEditContract.Effect.NavigateBack }
                }
            }
        }
    }
}
