package com.ssafy.neegongnaegong.presentation.group.vote

import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.vote.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class VoteViewModel
@Inject
constructor(

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

    override fun handleEvent(event: VoteContract.Event) {
        when (event) {
            VoteContract.Event.OnClickAddVoteItemButton -> {
                if (uiState.value.voteItemList.size == 10) {
                    setEffect { VoteContract.Effect.ShowToast("항목은 최대 10개까지만 가능합니다!") }
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

            VoteContract.Event.OnClickCompleteButton -> {
                setEffect { VoteContract.Effect.NavigateToBackStack }
                TODO("API 호출 후 Navigation")
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
