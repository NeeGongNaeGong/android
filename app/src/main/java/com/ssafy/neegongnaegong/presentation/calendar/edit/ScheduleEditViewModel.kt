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
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    private val getScheduleDetailUseCase: GetScheduleDetailUseCase,
    private val updatePersonalSchedulesUseCase: UpdatePersonalSchedulesUseCase,
) : BaseViewModel<ScheduleEditContract.Event, ScheduleEditContract.State, ScheduleEditContract.Effect>() {

    override fun createInitialState(): ScheduleEditContract.State = ScheduleEditContract.State()

    override fun handleEvent(event: ScheduleEditContract.Event) {
        when (event) {
            is ScheduleEditContract.Event.OnLoad -> onLoad(event.scheduleId, event.date)
            is ScheduleEditContract.Event.OnSaveScheduleClicked -> setState { copy(isUpdateTypeSelectorShow = true) }
            is ScheduleEditContract.Event.OnTitleChanged -> setSchedule(title = event.title)
            is ScheduleEditContract.Event.OnContentChanged -> setSchedule(content = event.content)
            is ScheduleEditContract.Event.OnStartAtChanged -> setSchedule(startAt = event.at)
            is ScheduleEditContract.Event.OnEndAtChanged -> setSchedule(endAt = event.at)
            is ScheduleEditContract.Event.OnLocationChanged -> setSchedule(location = event.location)
            is ScheduleEditContract.Event.OnRepeatRuleChanged -> setSchedule(repeatRule = event.repeatRule)
            is ScheduleEditContract.Event.OnCancelClick -> setEffect { ScheduleEditContract.Effect.NavigateBack }
            is ScheduleEditContract.Event.OnDialogDismissed -> setState { copy(isUpdateTypeSelectorShow = false) }
            is ScheduleEditContract.Event.OnUpdateTypeSelected -> saveSchedule(event.type)
        }
    }

    private fun onLoad(scheduleId: Long, date: LocalDate) = viewModelScope.launch {
        getScheduleDetailUseCase(scheduleId, date).withLoading {
            setState { copy(isLoading = it) }
        }.safeCollect { schedule ->
            setState {
                copy(
                    initSchedule = schedule,
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
        startAt: LocalDateTime = uiState.value.schedule.startAt,
        endAt: LocalDateTime = uiState.value.schedule.endAt,
        location: String? = uiState.value.schedule.location,
        repeatRule: RepeatRuleInfo? = uiState.value.repeatRule,
    ) {
        setState {
            copy(
                schedule = ScheduleInfo(
                    title = title,
                    content = content,
                    startAt = startAt,
                    endAt = endAt,
                    location = location,
                ),
                repeatRule = repeatRule
            )
        }
    }

    private fun saveSchedule(type: UpdateType) = viewModelScope.launch {
        setState { copy(isUpdateTypeSelectorShow = false) }
        with(uiState.value) {
            if (id == null) {
                showErrorMessage("데이터가 회손되었습니다.")
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
