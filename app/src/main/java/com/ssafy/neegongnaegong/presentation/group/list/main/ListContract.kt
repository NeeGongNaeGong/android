package com.ssafy.neegongnaegong.presentation.group.list.main

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class ListContract {
    sealed interface Event : UiEvent {
        data class OnClickAddContent(val currentPage: Int) : Event

        // Record Screen의 인자가 제대로 들어오지 않은 경우
        data object InvalidAccess : Event
    }

    data class State(
        val groupId: Long,
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToBackStack : Effect

        data object NavigateToNoticeDetailScreen : Effect

        data object NavigateToVoteDetailScreen : Effect
    }
}
