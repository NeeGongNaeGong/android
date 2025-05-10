//package com.ssafy.neegongnaegong.presentation.group.record
//
//import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
//import com.ssafy.neegongnaegong.presentation.util.TimeFormatter
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.collections.immutable.persistentListOf
//import java.time.LocalDateTime
//import javax.inject.Inject
//
//@HiltViewModel
//class RecordViewModel
//@Inject
//constructor(
//
//) : BaseViewModel<RecordContract.Event, RecordContract.State, RecordContract.Effect>() {
//    override fun createInitialState(): RecordContract.State =
//        RecordContract.State(
//            isMultipleSelectionEnabled = false,
//            isAnonymousVotingEnabled = false,
//            allowAddingSelection = false,
//            isEndDateEnabled = false,
//            isAlarmBeforeClosingEnabled = false,
//            voteTitle = "",
//            voteItemList = persistentListOf("", "", ""),
//            date = TimeFormatter.convertLocalDateTimeToStringDate(LocalDateTime.now()),
//            time = TimeFormatter.convertLocalDateTimeToStringTime(LocalDateTime.now()),
//            isDateDialogVisible = false,
//            isTimeDialogVisible = false
//        )
//
//    override fun handleEvent(event: RecordContract.Event) {
//        when (event) {
//            RecordContract.Event.OnClickAddVoteItemButton -> {
//                if (uiState.value.voteItemList.size == 10) {
//                    setEffect { RecordContract.Effect.ShowToast("항목은 최대 10개까지만 가능합니다!") }
//                } else {
//                    setState { copy(voteItemList = voteItemList.add("")) }
//                }
//            }
//
//            RecordContract.Event.OnClickAlarmBeforeClosingOption -> {
//                setState { copy(isAlarmBeforeClosingEnabled = !isAlarmBeforeClosingEnabled) }
//            }
//
//            RecordContract.Event.OnClickAllowAddingSelectionOption -> {
//                setState { copy(allowAddingSelection = !allowAddingSelection) }
//            }
//
//            RecordContract.Event.OnClickAnonymousVotingOption -> {
//                setState { copy(isAnonymousVotingEnabled = !isAnonymousVotingEnabled) }
//            }
//
//            RecordContract.Event.OnClickCompleteButton -> {
//                setEffect { RecordContract.Effect.NavigateToBackStack }
//                TODO("API 호출 후 Navigation")
//            }
//
//            RecordContract.Event.OnClickEndDateOption -> {
//                setState { copy(isEndDateEnabled = !isEndDateEnabled) }
//            }
//
//            RecordContract.Event.OnClickMultipleSelectionOption -> {
//                setState { copy(isMultipleSelectionEnabled = !isMultipleSelectionEnabled) }
//            }
//
//            is RecordContract.Event.OnVoteItemChanged -> {
//                val (index, title) = event
//                setState {
//                    copy(
//                        voteItemList = voteItemList.set(index, title)
//                    )
//                }
//            }
//
//            is RecordContract.Event.OnVoteTitleChanged -> {
//                val title = event.title
//                setState { copy(voteTitle = title) }
//
//            }
//
//            RecordContract.Event.OnClickDateButton -> {
//                setState { copy(isDateDialogVisible = true) }
//            }
//
//            RecordContract.Event.OnClickTimeButton -> {
//                setState { copy(isTimeDialogVisible = true) }
//            }
//
//            RecordContract.Event.OnDismissDateButton -> {
//                setState { copy(isDateDialogVisible = false) }
//            }
//
//            RecordContract.Event.OnDismissTimeButton -> {
//                setState { copy(isTimeDialogVisible = false) }
//            }
//
//            is RecordContract.Event.OnChangeDate -> {
//                setState { copy(date = TimeFormatter.convertLongToDateString(event.date)) }
//            }
//
//            is RecordContract.Event.OnChangeTime -> {
//                event.apply {
//                    setState {
//                        copy(
//                            time = TimeFormatter.convertHourMinuteToAmPmFormat(
//                                event.hour,
//                                event.minute
//                            )
//                        )
//                    }
//                }
//
//            }
//        }
//    }
//}
