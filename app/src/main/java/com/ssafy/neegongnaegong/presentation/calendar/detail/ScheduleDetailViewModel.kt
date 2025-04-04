package com.ssafy.neegongnaegong.presentation.calendar.detail

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.usecase.calendar.DeletePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val deletePersonalSchedulesUseCase: DeletePersonalSchedulesUseCase
) : BaseViewModel<ScheduleDetailContract.Event, ScheduleDetailContract.State, ScheduleDetailContract.Effect>() {

    override fun createInitialState(): ScheduleDetailContract.State = ScheduleDetailContract.State()

    override fun handleEvent(event: ScheduleDetailContract.Event) {
        when (event) {
            is ScheduleDetailContract.Event.OnLoad -> onLoad(event.date, event.schedule)
            ScheduleDetailContract.Event.OnEditClick -> navigateToEditScheduleScreen()
            is ScheduleDetailContract.Event.OnDeleteClick -> deleteSchedule(event.type)
        }
    }

    private fun onLoad(date: LocalDate, schedule: Schedule) {
        setState {
            copy(date = date, schedule = schedule)
        }
    }

    private fun navigateToEditScheduleScreen() {
        with(uiState.value) {
            if (schedule == null) {
                setEffect { ScheduleDetailContract.Effect.ShowErrorSnackBar("데이터가 회손되었습니다.") }
                setEffect { ScheduleDetailContract.Effect.NavigateBack }
            } else {
                setEffect { ScheduleDetailContract.Effect.NavigateToEditScheduleScreen(schedule) }
            }
        }
    }

    private fun deleteSchedule(type: DeleteType) = viewModelScope.launch {
        with(uiState.value) {
            if (date == null || schedule == null) {
                setEffect { ScheduleDetailContract.Effect.ShowErrorSnackBar("데이터가 회손되었습니다.") }
                setEffect { ScheduleDetailContract.Effect.NavigateBack }
            } else {
                deletePersonalSchedulesUseCase(schedule.id, type, date)
            }
        }
    }
}
