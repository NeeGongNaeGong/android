package com.ssafy.neegongnaegong.presentation.personal

import androidx.compose.runtime.Stable
import com.ssafy.neegongnaegong.domain.model.learning.LearningRecord
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import java.time.LocalDate

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

        // Delete selected
        data class OnDeleteSelect(
            val recordId: Long,
        ) : Event()

        data object OnSelectModeChange : Event()

        data object OnSelectCancel : Event()

        data object OnSelectDelete : Event()

        data object OnSelectDialogConfirm : Event()

        data object OnSelectDialogCancel : Event()

        // Date
        data class OnDateSelected(
            val date: String,
        ) : Event()
    }

    @Stable
    data class State(
        // Drop menu
        val isTagScreen: Boolean = true,
        val isDateScreen: Boolean = false,
        // tag
        val tags: ImmutableList<Tag> = persistentListOf(),
        val selectedTags: ImmutableList<Tag> = persistentListOf(),
        val unSelectedTags: ImmutableList<Tag> = persistentListOf(),
        val isConfirmButtonEnabled: Boolean = false,
        val isDialogShow: Boolean = false,
        val selectedRecordsByTag: ImmutableList<LearningRecord> = persistentListOf(),
        // calendar
        val selectedDate: String = "",
        val selectedRecordsByDate: ImmutableList<LearningRecord> = persistentListOf(),
        val learningDates: ImmutableSet<LocalDate> = persistentSetOf(),
        val currentMonth: String = "",
        // api
        val isLoading: Boolean = false,
        // api tag
        val hasTagDataNext: Boolean = false,
        val tagCursorId: Long? = null,
        val tagCursorValue: String? = null,
        // api date
        val hasDateDataNext: Boolean = false,
        val dateCursorId: Long? = null,
        val dateCursorValue: String? = null,
        // delete selected
        val isSelectedMode: Boolean = false,
        val deleteSelectedRecordIds: Set<Long> = emptySet(),
        val isDeleteDialogShow: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowErrorToast(
            val message: String,
        ) : Effect()

        data object ShowTagLimitExceededToast : Effect()
    }
}
