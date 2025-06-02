package com.ssafy.neegongnaegong.presentation.calendar.detail

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.usecase.calendar.DeletePersonalSchedulesUseCase
import com.ssafy.neegongnaegong.domain.usecase.calendar.GetScheduleDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.calendar.component.form.ScheduleInputFormFocus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ScheduleDetailViewModel @Inject constructor(
    private val getScheduleDetailUseCase: GetScheduleDetailUseCase,
    private val deletePersonalSchedulesUseCase: DeletePersonalSchedulesUseCase
) : BaseViewModel<ScheduleDetailContract.Event, ScheduleDetailContract.State, ScheduleDetailContract.Effect>() {

    override fun createInitialState(): ScheduleDetailContract.State = ScheduleDetailContract.State()

    override fun handleEvent(event: ScheduleDetailContract.Event) {
        when (event) {
            is ScheduleDetailContract.Event.OnLoad -> onLoad(event.scheduleId, event.date)
            ScheduleDetailContract.Event.OnEditClick -> navigateToEditScheduleScreen()
            is ScheduleDetailContract.Event.OnDeleteClick -> setState { copy(isDeleteTypeSelectorShow = true) }
            is ScheduleDetailContract.Event.OnFormClick -> navigateToEditScheduleScreen(event.focus)
            is ScheduleDetailContract.Event.OnDeleteTypeSelected -> deleteSchedule(event.type)
            ScheduleDetailContract.Event.OnDialogDismissed -> setState { copy(isDeleteTypeSelectorShow = false) }
        }
    }

    private fun onLoad(scheduleId: Long, date: LocalDate) = viewModelScope.launch {
        getScheduleDetailUseCase(scheduleId, date).withLoading {
            setState { copy(isLoading = it) }
        }.safeCollect { result ->
            setState { copy(schedule = result) }
        }
    }

    private fun navigateToEditScheduleScreen(
        focus: ScheduleInputFormFocus = ScheduleInputFormFocus.None
    ) {
        with(uiState.value) {
            setEffect {
                ScheduleDetailContract.Effect.NavigateToEditScheduleScreen(schedule, focus)
            }
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
