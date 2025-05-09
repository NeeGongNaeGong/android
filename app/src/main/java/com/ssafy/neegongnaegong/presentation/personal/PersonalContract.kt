package com.ssafy.neegongnaegong.presentation.personal

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class PersonalContract {
    sealed class Event : UiEvent {
        // DropDown
        data object OnTagScreenSelected : Event()

        data object OnDateScreenSelected : Event()

        // Tag
        data class OnTagEraseClicked(
            val tag: Tag,
        ) : Event()

        data class OnTagSelected(
            val tag: Tag,
        ) : Event()

        data class OnTagDeselected(
            val tag: Tag,
        ) : Event()

        data class OnSearchTextChanged(
            val query: String,
        ) : Event()

        data object OnTagPlusClicked : Event()

        data object OnDialogClose : Event()

        data object OnDialogConfirmClicked : Event()

        data object OnDialogCancelClicked : Event()

        // Record
        data object OnRecordLoadMore : Event()

        data object OnRecordRefresh : Event()

        // Date
        data class OnDateSelected(
            val date: String,
        ) : Event()
    }

    data class State(
        // Drop menu
        val isTagScreen: Boolean = true,
        val isDateScreen: Boolean = false,
        // tag
        val tags: List<Tag> = emptyList(),
        val selectedTags: List<Tag> = emptyList(),
        val unSelectedTags: List<Tag> = emptyList(),
        val isConfirmButtonEnabled: Boolean = false,
        val isDialogShow: Boolean = false,
        val selectedRecordsByTag: List<LearningRecord> = emptyList(),
        // study
        val learningRecords: List<LearningRecord> = emptyList(),
        // calendar
        val selectedDate: String = "",
        val selectedRecordsByDate: List<LearningRecord> = emptyList(),
        // api
        val isLoading: Boolean = false,
        val cursorId: Long? = null,
        val cursorCreatedAt: String? = null,
        val hasNext: Boolean = true,
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorToast(
            val message: String,
        ) : Effect()

        data object ShowTagLimitExceededToast : Effect()
    }
}
