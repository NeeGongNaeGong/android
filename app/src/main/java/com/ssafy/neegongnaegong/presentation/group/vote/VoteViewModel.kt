package com.ssafy.neegongnaegong.presentation.group.vote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.studies.VoteInfo
import com.ssafy.neegongnaegong.domain.usecase.studies.CreateVoteUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import com.ssafy.neegongnaegong.presentation.util.TimeFormatter
import com.ssafy.neegongnaegong.presentation.util.TimeFormatter.convertStringToLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class VoteViewModel @Inject constructor(
    private val createVoteUseCase: CreateVoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<VoteContract.Event, VoteContract.State, VoteContract.Effect>() {
    override fun createInitialState(): VoteContract.State =
        VoteContract.State(
            isMultipleSelectionEnabled = false,
            isAnonymousVotingEnabled = false,
            allowAddingSelection = false,
            isEndDateEnabled = false,
            isAlarmBeforeClosingEnabled = false,
            voteTitle = "",
            voteItemList = persistentListOf("", "", ""),
            date = TimeFormatter.convertLocalDateTimeToStringDate(LocalDateTime.now()),
            time = TimeFormatter.convertLocalDateTimeToStringTime(LocalDateTime.now()),
            isDateDialogVisible = false,
            isTimeDialogVisible = false
        )

    override fun handleException(e: Throwable, errorContext: ErrorContext, retry: () -> Unit) {
        if (errorContext is VoteContract.Error) {
            when (errorContext) {
                VoteContract.Error.CreateVoteError -> {
                    showErrorMessage(
                        e.message ?: "투표 생성에 실패했습니다",
                        SnackbarManager.Action.retry { retry() }
                    )
                }
            }
        }
    }

    override fun handleEvent(event: VoteContract.Event) {
        when (event) {
            VoteContract.Event.OnClickAddVoteItemButton -> {
                if (uiState.value.voteItemList.size == 10) {
                    showWarningMessage("항목은 최대 10개까지만 가능합니다!")
                } else {
                    setState { copy(voteItemList = voteItemList.add("")) }
                }
            }

            VoteContract.Event.OnClickAlarmBeforeClosingOption -> {
                setState { copy(isAlarmBeforeClosingEnabled = !isAlarmBeforeClosingEnabled) }
            }

            VoteContract.Event.OnClickAllowAddingSelectionOption -> {
                setState { copy(allowAddingSelection = !allowAddingSelection) }
            }

            VoteContract.Event.OnClickAnonymousVotingOption -> {
                setState { copy(isAnonymousVotingEnabled = !isAnonymousVotingEnabled) }
            }

            is VoteContract.Event.OnClickCompleteButton -> {
                viewModelScope.launch {
                    createVoteUseCase(
                        studyId = savedStateHandle["studyGroupId"]!!,
                        uiState.value.run {
                            VoteInfo(
                                title = voteTitle,
                                startTime = LocalDateTime.now(), //getCurrentTimeByISO8601Format(),
                                endTime = if(isEndDateEnabled) {convertStringToLocalDateTime(date, time)} else null,
                                state = true,
                                items = voteItemList.filter { it.isNotEmpty() },
                                multiple = isMultipleSelectionEnabled,
                                secret = isAnonymousVotingEnabled,
                                notify = isAlarmBeforeClosingEnabled,
                                choose = allowAddingSelection
                            )
                        }
                    ).safeCollect(VoteContract.Error.CreateVoteError) {
                        showSuccessMessage("투표를 생성했습니다!")
                        setEffect { VoteContract.Effect.NavigateToBackStack }
                    }
                }
            }

            VoteContract.Event.OnClickEndDateOption -> {
                setState { copy(isEndDateEnabled = !isEndDateEnabled) }
            }

            VoteContract.Event.OnClickMultipleSelectionOption -> {
                setState { copy(isMultipleSelectionEnabled = !isMultipleSelectionEnabled) }
            }

            is VoteContract.Event.OnVoteItemChanged -> {
                val (index, title) = event
                setState {
                    copy(
                        voteItemList = voteItemList.set(index, title)
                    )
                }
            }

            is VoteContract.Event.OnVoteTitleChanged -> {
                val title = event.title
                setState { copy(voteTitle = title) }

            }

            VoteContract.Event.OnClickDateButton -> {
                setState { copy(isDateDialogVisible = true) }
            }

            VoteContract.Event.OnClickTimeButton -> {
                setState { copy(isTimeDialogVisible = true) }
            }

            VoteContract.Event.OnDismissDateButton -> {
                setState { copy(isDateDialogVisible = false) }
            }

            VoteContract.Event.OnDismissTimeButton -> {
                setState { copy(isTimeDialogVisible = false) }
            }

            is VoteContract.Event.OnChangeDate -> {
                setState { copy(date = TimeFormatter.convertLongToDateString(event.date)) }
            }

            is VoteContract.Event.OnChangeTime -> {
                event.apply {
                    setState {
                        copy(
                            time = TimeFormatter.convertHourMinuteToAmPmFormat(
                                event.hour,
                                event.minute
                            )
                        )
                    }
                }

            }
        }
    }
}
