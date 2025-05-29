package com.ssafy.neegongnaegong.presentation.group.list.vote

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class VoteContract {
    sealed interface Event : UiEvent {
        // Record Screen의 인자가 제대로 들어오지 않은 경우
        data object InvalidAccess : Event
    }

    data object State : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToBackStack : Effect
    }
}
