package com.ssafy.neegongnaegong.presentation.calendar.detail

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.usecase.calendar.DeletePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetScheduleDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val getScheduleDetailUseCase: GetScheduleDetailUseCase,
    private val deletePersonalSchedulesUseCase: DeletePersonalSchedulesUseCase
) : BaseViewModel<ScheduleDetailContract.Event, ScheduleDetailContract.State, ScheduleDetailContract.Effect>() {

    override fun createInitialState(): ScheduleDetailContract.State = ScheduleDetailContract.State()

    override fun handleEvent(event: ScheduleDetailContract.Event) {
        when (event) {
            is ScheduleDetailContract.Event.OnLoad -> onLoad(event.scheduleId)
            ScheduleDetailContract.Event.OnEditClick -> navigateToEditScheduleScreen()
            is ScheduleDetailContract.Event.OnDeleteClick -> deleteSchedule(event.type)
        }
    }

    private fun onLoad(scheduleId: Long) = viewModelScope.launch {
        getScheduleDetailUseCase(scheduleId).withLoading {
            setState { copy(isLoading = it) }
        }.safeCollect { result ->
            setState { copy(schedule = result) }
        }
    }

    private fun navigateToEditScheduleScreen() {
        with(uiState.value) {
            setEffect { ScheduleDetailContract.Effect.NavigateToEditScheduleScreen(schedule) }
        }
    }

    private fun deleteSchedule(type: DeleteType) = viewModelScope.launch {
        with(uiState.value) {
            deletePersonalSchedulesUseCase(
                schedule.id,
                type,
                schedule.info.startAt.toLocalDate()
            ).withLoading {
                setState { copy(isOnDelete = it) }
            }.safeCollect {
                setEffect { ScheduleDetailContract.Effect.NavigateBack }
            }
        }
    }
}
