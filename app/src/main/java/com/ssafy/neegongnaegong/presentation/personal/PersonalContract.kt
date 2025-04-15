package com.ssafy.neegongnaegong.presentation.personal

import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class PersonalContract {
    sealed class Event : UiEvent {
        // DropDown
        data object OnTagScreenSelected : UiEvent
        data object OnDateScreenSelected : UiEvent
        // Tag
        data class OnTagEraseClicked(val tag: Tag) : UiEvent
        data class OnTagSelected(val tag: Tag) : UiEvent
        data class OnTagDeselected(val tag: Tag) : UiEvent
        data class OnSearchTextChanged(val query: String) : UiEvent
        data object OnTagPlusClicked : UiEvent
        data object OnDialogClose : UiEvent
        data object OnDialogConfirmClicked : UiEvent
        data object OnDialogCancelClicked : UiEvent
        // Record

    }

    data class State(
        val isTag: Boolean = true,
        val isDate: Boolean = false,
        val selectedTags: List<Tag> = emptyList(),
        val searchQuery: String = "",
        val isDialogOpen: Boolean = false
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorToast(val message: String) : Effect()
    }
}
