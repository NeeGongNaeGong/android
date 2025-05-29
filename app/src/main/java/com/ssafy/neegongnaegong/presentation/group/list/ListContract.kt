package com.ssafy.neegongnaegong.presentation.group.list

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

enum class Index(val index: Int, val title: String) {
    Notice(0, "공지"),
    Vote(1, "투표"),
}

class ListContract {
    sealed interface Event : UiEvent {
        data object OnClickAddContent : Event

        data class OnClickTab(val tab: Index) : Event
    }

    data class State(
        val index: Index,
    ) : UiState

    sealed class Effect : UiEffect
}
