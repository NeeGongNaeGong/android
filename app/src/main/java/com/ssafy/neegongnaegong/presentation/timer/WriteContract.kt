package com.ssafy.neegongnaegong.presentation.timer

import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class WriteContract {
    sealed class Event : UiEvent {
        data object OnCancelClicked : Event()
        data object OnConfirmClicked : Event()
        data class OnTitleChanged(val title: String) : Event()
        data class OnContentChanged(val content: String) : Event()
        data object OnTagPlusClicked : Event()
        data object OnTagEraseClicked : Event()
    }

    data class State(
        val title: String = "",
        val content: String = "",
        val tags: List<String> = emptyList(),
        val isConfirmButtonEnabled: Boolean = false,
        val startTime: String = "",
        val endTime: String = "",
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToHome : Effect()
        data class ShowSuccessToast(val message: String) : Effect()
        data class ShowErrorToast(val message: String) : Effect()
    }
}
