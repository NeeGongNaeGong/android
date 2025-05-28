package com.ssafy.neegongnaegong.presentation.group.list

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class ListContract {
    sealed class Event : UiEvent

    data class State(
        val index: Int,
    ) : UiState

    sealed class Effect : UiEffect
}
