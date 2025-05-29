package com.ssafy.neegongnaegong.presentation.group.list.main

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import com.ssafy.neegongnaegong.presentation.navigation.Index

class ListContract {
    sealed interface Event : UiEvent {
        data object OnClickAddContent : Event

        data class OnClickTab(val tab: Index) : Event

        // Record Screen의 인자가 제대로 들어오지 않은 경우
        data object InvalidAccess : Event
    }

    data class State(
        val index: Index,
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToBackStack : Effect
    }
}
