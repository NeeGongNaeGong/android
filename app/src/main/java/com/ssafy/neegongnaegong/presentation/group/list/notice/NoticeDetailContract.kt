package com.ssafy.neegongnaegong.presentation.group.list.notice

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class NoticeDetailContract {
    sealed interface Event : UiEvent {
        // Record Screen의 인자가 제대로 들어오지 않은 경우
        data object InvalidAccess : Event
    }

    data class State(
        val writer: String,
        val writerProfileImage: String,
        val createdAt: String,
        val content: String,
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToBackStack : Effect
    }
}
