package com.ssafy.neegongnaegong.presentation.group.vote

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import kotlinx.collections.immutable.PersistentList

class VoteContract {
    sealed class Event : UiEvent {
        data object OnClickAddVoteItemButton : Event()
        data object OnClickCompleteButton : Event()
        data object OnClickMultipleSelectionOption : Event()
        data object OnClickAnonymousVotingOption : Event()
        data object OnClickAllowAddingSelectionOption : Event()
        data object OnClickEndDateOption : Event()
        data object OnClickAlarmBeforeClosingOption : Event()
        data class OnVoteTitleChanged(val title: String) : Event()
        data class OnVoteItemChanged(val index: Int, val title: String) : Event()
        data object OnClickDateButton : Event()
        data object OnClickTimeButton : Event()
        data object OnDismissDateButton : Event()
        data object OnDismissTimeButton : Event()
        data class OnChangeDate(val date: Long) : Event()
        data class OnChangeTime(val hour: Int, val minute: Int) : Event()
    }

    data class State(
        val isMultipleSelectionEnabled: Boolean,
        val isAnonymousVotingEnabled: Boolean,
        val allowAddingSelection: Boolean,
        val isEndDateEnabled: Boolean,
        val isAlarmBeforeClosingEnabled: Boolean,
        val voteTitle: String,
        val voteItemList: PersistentList<String>,
        val date: String,
        val time: String,

        val isDateDialogVisible: Boolean,
        val isTimeDialogVisible: Boolean
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToBackStack : Effect()
    }
}
