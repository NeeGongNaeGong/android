package com.ssafy.neegongnaegong.presentation.group.list.main

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class ListContract {
    sealed interface Event : UiEvent {
        data class OnClickAddContent(val currentPage: Int) : Event

        // Record Screen의 인자가 제대로 들어오지 않은 경우
        data object InvalidAccess : Event

        data class OnClickNoticeItem(val noticeId: Long) : Event

        data class OnClickVoteItem(val voteId: Long) : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToBackStack : Effect

        data class NavigateToNoticeDetailScreen(val noticeId: Long) : Effect

        data class NavigateToVoteDetailScreen(val voteId: Long) : Effect

        data object NavigateToMakeNotice : Effect

        data object NavigateToMakeVote : Effect
    }

    data class State(
        val groupId: Long,
    ) : UiState

    enum class Index(
        val index: Int,
        val title: String,
    ) {
        Notice(
            index = 0,
            title = "공지",
        ),
        Vote(
            index = 1,
            title = "투표",
        ),
    }
}
