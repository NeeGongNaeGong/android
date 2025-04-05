package com.ssafy.neegongnaegong.presentation.calendar.edit

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetScheduleDetailUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.UpdatePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
            is ScheduleEditContract.Event.OnIsAllDayChanged -> setSchedule(isAllDay = event.isAllDay)
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
                    date = schedule.info.startDate.toLocalDate(),
                    schedule = schedule.info,
                    repeatRule = schedule.info.repeatRule?.info,
                )
            }
        }
    }

    private fun setSchedule(
        title: String = uiState.value.schedule?.title ?: "내 일정",
        content: String? = uiState.value.schedule?.content,
        startDate: LocalDateTime = uiState.value.schedule?.startDate
            ?: uiState.value.date?.atStartOfDay()
            ?: LocalDateTime.now(),
        endDate: LocalDateTime = uiState.value.schedule?.endDate
            ?: uiState.value.date?.atStartOfDay()?.plusDays(1)?.minusSeconds(1)
            ?: LocalDateTime.now(),
        isAllDay: Boolean = uiState.value.schedule?.isAllDay ?: false,
        location: String? = uiState.value.schedule?.location,
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

    private fun saveSchedule(type: UpdateType) = viewModelScope.launch {
        with(uiState.value) {
            if (id == null || schedule == null || date == null) {
                setEffect {
                    ScheduleEditContract.Effect.ShowErrorSnackBar("데이터가 회손되었습니다.")
                }
            } else {
                updatePersonalSchedulesUseCase(
                    id = id,
                    schedule = schedule,
                    repeatRule = repeatRule,
                    type = type,
                    date = date,
                ).safeCollect {
                    setEffect { ScheduleEditContract.Effect.NavigateBack }
                }
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
                ScheduleEditContract.Effect.ShowErrorSnackBar(error.message ?: "에러 발생")
            }
        }
    }
}
