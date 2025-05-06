package com.ssafy.neegongnaegong.presentation.timer.learning

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.data.TagData
import com.ssafy.neegongnaegong.domain.model.learning.Tag
import com.ssafy.neegongnaegong.domain.usecase.learningrecord.CreateLearningRecordUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningRecordWriteViewModel
    @Inject
    constructor(
        private val createLearningRecordUseCase: CreateLearningRecordUseCase,
    ) : BaseViewModel<LearningRecordWriteContract.Event, LearningRecordWriteContract.State, LearningRecordWriteContract.Effect>() {
        override fun createInitialState(): LearningRecordWriteContract.State = LearningRecordWriteContract.State()

        override fun handleEvent(event: LearningRecordWriteContract.Event) {
            when (event) {
                // 글 작성
                is LearningRecordWriteContract.Event.OnTitleChanged -> {
                    setState { copy(learningRecord = learningRecord.copy(title = event.title)) }
                }

                is LearningRecordWriteContract.Event.OnContentChanged -> {
                    setState { copy(learningRecord = learningRecord.copy(content = event.content)) }
                }

                // 취소, 확인
                is LearningRecordWriteContract.Event.OnCancelClicked -> {
                }

                is LearningRecordWriteContract.Event.OnConfirmClicked -> {
                }

                // 태그 추가,삭제
                is LearningRecordWriteContract.Event.OnTagEraseClicked -> {
                    deleteTag(event.tag)
                }

                is LearningRecordWriteContract.Event.OnTagSelected -> {
                    selectTag(event.tag)
                }

                is LearningRecordWriteContract.Event.OnTagDeselected -> {
                    deselectTag(event.tag)
                }

                is LearningRecordWriteContract.Event.OnSearchTextChanged -> {
                    updateDialogTags(event.query)
                }

                is LearningRecordWriteContract.Event.OnSearchTextChangedWithKmp -> {
                    updateDialogTagsWithKmp(event.query)
                }

                is LearningRecordWriteContract.Event.OnTagPlusClicked -> {
                    moveFromTagsToSelectedTags()
                    setState { copy(isDialogShow = true) }
                }

                is LearningRecordWriteContract.Event.OnDialogClose -> {
                    clearDialogTags()
                    setState { copy(isDialogShow = false) }
                }

                is LearningRecordWriteContract.Event.OnDialogConfirmClicked -> {
                    if (checkTagSize()) {
                        setEffect { LearningRecordWriteContract.Effect.ShowTagLimitExceededToast }
                    } else {
                        moveFromSelectedTagsToTags()
                        clearDialogTags()
                        setState { copy(isDialogShow = false) }
                    }
                }

                is LearningRecordWriteContract.Event.OnDialogCancelClicked -> {
                    setState { copy(isDialogShow = false) }
                }
            }
        }

        // api

        private fun createLearningRecord() =
            viewModelScope.launch {
                createLearningRecordUseCase(
                    uiState.value.learningRecord,
                ).withLoading {

                }
            }

        // tag

        private fun moveFromTagsToSelectedTags() = setState { copy(selectedTags = uiState.value.tags, unSelectedTags = emptyList()) }

        private fun clearDialogTags() = setState { copy(selectedTags = emptyList(), unSelectedTags = emptyList()) }

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

        private fun checkTagSize(): Boolean {
            val tags = uiState.value.tags
            val selected = uiState.value.selectedTags

            val merged = (tags + selected).toSet()
            return merged.size > MAX_TAG_LIMIT
        }

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

        private fun updateDialogTags(query: String) {
            val filtered =
                if (query.isBlank()) {
                    emptyList()
                } else {
                    TagData.tags
                        .filterNot { tag ->
                            uiState.value.tags.contains(tag) ||
                                uiState.value.selectedTags.contains(
                                    tag,
                                )
                        }.filter { tag ->
                            tag.koName.contains(query) ||
                                tag.enName.contains(
                                    query,
                                    ignoreCase = true,
                                )
                        }.take(10)
                }

            setState { copy(unSelectedTags = filtered) }
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

        companion object {
            const val MAX_TAG_LIMIT = 5
        }
    }
