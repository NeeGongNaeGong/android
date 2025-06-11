package com.ssafy.neegongnaegong.presentation.group.list.vote

import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteStatusInfo
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Event
import kotlinx.collections.immutable.PersistentList

class VoteDetailContract {
    sealed interface Event : UiEvent {
        // Record Screen의 인자가 제대로 들어오지 않은 경우
        data object InvalidAccess : Event

        data class SelectOption(val voteItemId: Long, val voteItemName: String) : Event

        data object CastVote : Event

        data object ToggleCastMode : Event

        data class OnClickVotedPersonList(
            val title: String,
            val votedMemberInfo: List<StudyGroupVoteStatusInfo.VotedMemberInfo>,
        ) : Event

        data object OnClickAddOption : Event

        data class OnChangeNewOption(val optionTitle: String) : Event

        data object OnCancelAddOption : Event

        data object OnConfirmAddOption : Event

        data object OnClickPopBackStackButton : Event
    }

    enum class VoteOptions(val option: String, val description: String) {
        IS_SECRET("IS_SECRET", "익명 투표"),
        IS_OPEN("IS_OPEN", "진행 중"),
        IS_MULTIPLE("IS_MULTIPLE", "복수 선택"),
        IS_CHOSEN("IS_CHOSEN", "추가 항목 허용"),
        ;

        companion object {
            fun from(option: String): VoteOptions? = entries.find { it.option == option }
        }
    }

    data class State(
        val userName: String,
        val userProfileImg: String,
        val progressTime: String,
        val voteTitle: String,
        val state: Boolean,
        val selected: PersistentList<StudyGroupVoteDetailInfo.VoteValue>,
        val voteOptions: PersistentList<VoteOptions>,
        val voteItems: PersistentList<StudyGroupVoteStatusInfo>,
        val voteValues: PersistentList<StudyGroupVoteDetailInfo.VoteValue>,
        val castMode: Boolean,
        val addOptionMode: Boolean,
        val newOption: String,
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToBackStack : Effect

        data class NavigateToVotedPersonList(
            val title: String,
            val votedMemberInfo: List<StudyGroupVoteStatusInfo.VotedMemberInfo>,
        ) : Effect
    }
}
