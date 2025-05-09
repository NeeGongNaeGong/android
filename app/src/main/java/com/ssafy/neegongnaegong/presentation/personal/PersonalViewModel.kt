package com.ssafy.neegongnaegong.presentation.personal

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.data.TagData
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.usecase.learningrecord.GetLearningRecordListUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.timer.learning.LearningRecordWriteViewModel.Companion.MAX_TAG_LIMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel
    @Inject
    constructor(
        private val getLearningRecordListUseCase: GetLearningRecordListUseCase,
    ) : BaseViewModel<PersonalContract.Event, PersonalContract.State, PersonalContract.Effect>() {
        override fun createInitialState(): PersonalContract.State = PersonalContract.State()

        init {
            loadLearningRecords()
        }

        override fun handleEvent(event: PersonalContract.Event) {
            when (event) {
                // dropdown
                is PersonalContract.Event.OnDateScreenSelected -> {
                    setState {
                        copy(
                            isTagScreen = false,
                            isDateScreen = true,
                        )
                    }
                    filteringTodayRecord()
                }

                is PersonalContract.Event.OnTagScreenSelected -> {
                    setState {
                        copy(
                            isTagScreen = true,
                            isDateScreen = false,
                        )
                    }
                }
                // tag
                is PersonalContract.Event.OnTagEraseClicked -> {
                    deleteTag(event.tag)
                }

                is PersonalContract.Event.OnTagSelected -> {
                    selectTag(event.tag)
                }

                is PersonalContract.Event.OnTagDeselected -> {
                    deselectTag(event.tag)
                }

                is PersonalContract.Event.OnSearchTextChanged -> {
                    updateDialogTagsWithKmp(event.query)
                }

                is PersonalContract.Event.OnTagPlusClicked -> {
                    moveFromTagsToSelectedTags()
                    setState { copy(isDialogShow = true) }
                }

                is PersonalContract.Event.OnDialogClose -> {
                    clearDialogTags()
                    setState { copy(isDialogShow = false) }
                }

                is PersonalContract.Event.OnDialogConfirmClicked -> {
                    if (checkTagSize()) {
                        setEffect { PersonalContract.Effect.ShowTagLimitExceededToast }
                    } else {
                        moveFromSelectedTagsToTags()
                        clearDialogTags()
                        updateSelectedRecordsByTag()
                        setState { copy(isDialogShow = false) }
                    }
                }

                is PersonalContract.Event.OnDialogCancelClicked -> {
                    setState { copy(isDialogShow = false) }
                }

                // calendar
                is PersonalContract.Event.OnDateSelected -> {
                    filteringRecordByDate(event.date)
                }

                // paging
                is PersonalContract.Event.OnRecordLoadMore -> {
                    loadNextRecords()
                }

                is PersonalContract.Event.OnRecordRefresh -> {
                    loadLearningRecords()
                }
            }
        }
        // api

        private fun loadLearningRecords(
            tagIds: List<Long>? = null,
            date: String? = null,
        ) {
            viewModelScope.launch {
                setState { copy(isLoading = true) }
                getLearningRecordListUseCase(
                    tag = tagIds,
                    targetDate = date,
                    size = 20,
                ).safeCollect { result ->
                    setState {
                        copy(
                            learningRecords = result.content.map { it.toDomain() },
                            hasNext = result.hasNext,
                            cursorId = result.cursorId,
                            cursorCreatedAt = result.cursorCreatedAt,
                            isLoading = false,
                        )
                    }
                }
            }
        }

        private fun loadNextRecords() {
            val state = uiState.value
            if (!state.hasNext || state.isLoading) return

            viewModelScope.launch {
                setState { copy(isLoading = true) }
                getLearningRecordListUseCase(
                    cursorId = state.cursorId,
                    cursorCreatedAt = state.cursorCreatedAt,
                ).safeCollect { result ->
                    setState {
                        copy(
                            learningRecords = learningRecords + result.content.map { it.toDomain() },
                            hasNext = result.hasNext,
                            cursorId = result.cursorId,
                            cursorCreatedAt = result.cursorCreatedAt,
                            isLoading = false,
                        )
                    }
                }
            }
        }

        // calendar
        private fun filteringRecordByDate(date: String) {
            val filtered =
                uiState.value.learningRecords.filter {
                    it.startAt.toString().substring(0, 10) == date
                }
            setState {
                copy(
                    selectedDate = date,
                    selectedRecordsByDate = filtered,
                )
            }
        }

        private fun filteringTodayRecord() {
            val today = LocalDate.now().toString()

            val filtered =
                uiState.value.learningRecords.filter {
                    it.startAt.toString().substring(0, 10) == today
                }

            setState {
                copy(
                    selectedDate = today,
                    selectedRecordsByDate = filtered,
                )
            }
        }

        // tag

        private fun updateSelectedRecordsByTag() {
            val selectedTagIds =
                uiState.value.tags
                    .map { it.id }
                    .toSet()

            val filteredRecords =
                uiState.value.learningRecords.filter { record ->
                    record.tags.any { it.id in selectedTagIds }
                }

            setState {
                copy(selectedRecordsByTag = filteredRecords)
            }
        }

        private fun checkTagSize(): Boolean {
            val tags = uiState.value.tags
            val selected = uiState.value.selectedTags

            val merged = (tags + selected).toSet()
            return merged.size > MAX_TAG_LIMIT
        }

        private fun moveFromSelectedTagsToTags() {
            val tags = uiState.value.tags.toSet()
            val selected = uiState.value.selectedTags.toSet()

            val merged = tags + selected

            setState {
                copy(
                    tags = merged.toList(),
                    selectedTags = emptyList(),
                    unSelectedTags = emptyList(),
                )
            }
        }

        private fun moveFromTagsToSelectedTags() = setState { copy(selectedTags = uiState.value.tags, unSelectedTags = emptyList()) }

        private fun clearDialogTags() = setState { copy(selectedTags = emptyList(), unSelectedTags = emptyList()) }

        private fun deleteTag(tag: Tag) {
            val newTags = uiState.value.tags - tag
            setState { copy(tags = newTags) }
        }

        private fun selectTag(tag: Tag) {
            setState {
                copy(
                    selectedTags = uiState.value.selectedTags + tag,
                    unSelectedTags = uiState.value.unSelectedTags - tag,
                )
            }
        }

        private fun deselectTag(tag: Tag) {
            setState {
                copy(
                    selectedTags = uiState.value.selectedTags - tag,
                    unSelectedTags = uiState.value.unSelectedTags + tag,
                )
            }
        }

        private fun updateDialogTagsWithKmp(query: String) {
            if (query.isBlank()) {
                setState { copy(unSelectedTags = emptyList()) }
                return
            }

            val selected = uiState.value.tags
            val selectedInDialog = uiState.value.selectedTags

            val availableTags =
                TagData.tags.filterNot { selected.contains(it) || selectedInDialog.contains(it) }

            val startsWithList =
                availableTags.filter { tag ->
                    tag.koName.startsWith(query) || tag.enName.lowercase().startsWith(query.lowercase())
                }

            val kmpList =
                availableTags.filter { tag ->
                    kmp(tag.koName, query) || kmp(tag.enName.lowercase(), query.lowercase())
                }

            val merged =
                buildList {
                    addAll(startsWithList)
                    addAll(kmpList.filterNot { it in startsWithList })
                }.take(10)

            setState { copy(unSelectedTags = merged) }
        }

        private fun kmp(
            text: String,
            pattern: String,
        ): Boolean {
            if (pattern.isEmpty()) return false
            val pi = getPi(pattern)
            var j = 0
            for (i in text.indices) {
                while (j > 0 && text[i] != pattern[j]) {
                    j = pi[j - 1]
                }
                if (text[i] == pattern[j]) {
                    if (j == pattern.length - 1) {
                        return true
                    } else {
                        j++
                    }
                }
            }
            return false
        }

        private fun getPi(pattern: String): IntArray {
            val pi = IntArray(pattern.length)
            var j = 0
            for (i in 1 until pattern.length) {
                while (j > 0 && pattern[i] != pattern[j]) {
                    j = pi[j - 1]
                }
                if (pattern[i] == pattern[j]) {
                    j++
                    pi[i] = j
                }
            }
            return pi
        }
    }
