package com.ssafy.neegongnaegong.presentation.group.record

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import kotlinx.collections.immutable.PersistentList

class RecordContract {
    sealed class Event : UiEvent {

    }

    data object State : UiState

    sealed class Effect : UiEffect {
        data class ShowToast(
            val message: String,
        ) : Effect()
        data object NavigateToBackStack : Effect()
    }
}
