package com.ssafy.neegongnaegong.presentation.personal

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.data.mapper.learningrecord.LearningRecordMapper.toDomain
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
        override fun createInitialState(): PersonalContract.State = PersonalContract.State().copy(selectedDate = LocalDate.now().toString())

//        init {
// //            println("확인 호출 init")
//            // 이렇게 작성 하면 UnitTest 할때 단점이 있음
//            loadLearningRecords()
//        }

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
//                    println("확인 호출 OnDateScreenSelected")
//                    loadLearningRecords()
                }

                is PersonalContract.Event.OnTagScreenSelected -> {
                    setState {
                        copy(
                            isTagScreen = true,
                            isDateScreen = false,
                        )
                    }
//                    println("확인 호출 OnTagScreenSelected")
                    loadLearningRecords()
                }
                // tag
                is PersonalContract.Event.OnTagEraseClicked -> {
                    deleteTag(event.tag)
                    // println("확인 호출 OnTagEraseClicked")
                    loadLearningRecords()
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
//                      println("확인 호출 OnDialogConfirmClicked")
                        loadLearningRecords()
                        setState { copy(isDialogShow = false) }
                    }
                }

                is PersonalContract.Event.OnDialogCancelClicked -> {
                    setState { copy(isDialogShow = false) }
                }

                // calendar
                is PersonalContract.Event.OnDateSelected -> {
                    setState { copy(isTagScreen = false, isDateScreen = true) }
                    filteringRecordByDate(event.date)
                }

                // paging
                is PersonalContract.Event.OnRecordLoadMore -> {
                    loadNextRecords()
                }

                is PersonalContract.Event.OnRecordRefresh -> {
//                    println("확인 호출 OnRecordRefresh")
                    loadLearningRecords()
                }
            }
        }

        // api
        private fun loadLearningRecords() {
            viewModelScope.launch {
                if (uiState.value.isTagScreen) {
//                    println("확인 태그 보냄 ${uiState.value.tags}")
                    getLearningRecordListUseCase(
                        tag = uiState.value.tags.map { it.id },
                    ).withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState {
                            copy(
                                selectedRecordsByTag = result.content.toDomain(),
                                hasNext = result.hasNext,
                                cursorId = result.cursorId,
                                cursorCreatedAt = result.cursorCreatedAt,
                            )
                        }
                    }
                } else {
//                    println("확인 날짜 보냄 ${uiState.value.selectedDate}")
                    getLearningRecordListUseCase(
                        targetDate = uiState.value.selectedDate,
                    ).withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState {
                            copy(
                                selectedRecordsByDate = result.content.toDomain(),
                                hasNext = result.hasNext,
                                cursorId = result.cursorId,
                                cursorCreatedAt = result.cursorCreatedAt,
                            )
                        }
                    }
                }
            }
        }

        private fun loadNextRecords() {
            val state = uiState.value
            if (!state.hasNext || state.isLoading) return

            println("확인 아이템 더 출력")
            println("확인 상태확인 ${state.cursorCreatedAt} ${state.cursorId}")

            viewModelScope.launch {
                if (uiState.value.isTagScreen) {
                    getLearningRecordListUseCase(
                        tag = uiState.value.tags.map { it.id },
                        cursorId = state.cursorId,
                        cursorCreatedAt = state.cursorCreatedAt,
                    ).withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        println("확인 데이터 더 불러옴 ${result}")
                        setState {
                            val newRecords = result.content.toDomain()
                            val updatedList =
                                (selectedRecordsByTag + newRecords)
                                    .distinctBy { it.id }

                            copy(
                                selectedRecordsByTag = updatedList,
                                hasNext = result.hasNext,
                                cursorId = result.cursorId,
                                cursorCreatedAt = result.cursorCreatedAt,
                            )
                        }
                    }
                } else {
                    getLearningRecordListUseCase(
                        targetDate = uiState.value.selectedDate,
                        cursorId = state.cursorId,
                        cursorCreatedAt = state.cursorCreatedAt,
                    ).withLoading {
                        setState { copy(isLoading = it) }
                    }.safeCollect { result ->
                        setState {
                            val newRecords = result.content.toDomain()
                            val updatedList =
                                (selectedRecordsByDate + newRecords)
                                    .distinctBy { it.id }
                            copy(
                                selectedRecordsByDate = updatedList,
                                hasNext = result.hasNext,
                                cursorId = result.cursorId,
                                cursorCreatedAt = result.cursorCreatedAt,
                            )
                        }
                    }
                }
            }
        }

        // calendar
        private fun filteringRecordByDate(date: String) {
            setState {
                copy(selectedDate = date)
            }

//            println("확인 호출 filteringRecordByDate 함수")
            loadLearningRecords()
        }

        // tag
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
