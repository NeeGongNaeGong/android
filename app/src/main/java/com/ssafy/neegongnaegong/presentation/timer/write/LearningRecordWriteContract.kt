package com.ssafy.neegongnaegong.presentation.timer.write

import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class LearningRecordWriteContract {
    sealed class Event : UiEvent {
        data object OnCancelClicked : Event()

        data object OnConfirmClicked : Event()

        // record
        data class OnTitleChanged(
            val title: String,
        ) : Event()

        data class OnContentChanged(
            val content: String,
        ) : Event()

        // tag
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

        data class OnSearchTextChangedWithKmp(
            val query: String,
        ) : Event()

        data object OnTagPlusClicked : Event()

        data object OnDialogClose : Event()

        data object OnDialogConfirmClicked : Event()

        data object OnDialogCancelClicked : Event()
    }

    data class State(
        // study record
        val learningRecord: LearningRecord = LearningRecord.default(),
        // tag
        val tags: List<Tag> = emptyList(),
        val selectedTags: List<Tag> = emptyList(),
        val unSelectedTags: List<Tag> = emptyList(),
        val isConfirmButtonEnabled: Boolean = false,
        val isDialogShow: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToHome : Effect()

        data class ShowSuccessToast(
            val message: String,
        ) : Effect()

        data class ShowErrorToast(
            val message: String,
        ) : Effect()

        data object ShowTagLimitExceededToast : Effect()
    }
}
