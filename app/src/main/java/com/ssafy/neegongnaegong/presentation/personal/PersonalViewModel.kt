package com.ssafy.neegongnaegong.presentation.personal

import com.ssafy.neegongnaegong.domain.data.TagData
import com.ssafy.neegongnaegong.domain.model.personal.StudyRecord
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.timer.WriteViewModel.Companion.MAX_TAG_LIMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor() :
    BaseViewModel<PersonalContract.Event, PersonalContract.State, PersonalContract.Effect>() {

    override fun createInitialState(): PersonalContract.State {
        val dummyRecords = listOf(
            StudyRecord(
                "청산별곡 정주행",
                "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                "2025-04-19T04:33:02.856Z",
                "2025-04-19T06:33:02.856Z",
                listOf("CS", "네트워크")
            ),
            StudyRecord(
                "영어 단어 영어 단어 영어 단어",
                "VOCA 2200 암기",
                "2025-04-19T06:33:02.856Z",
                "2025-04-19T08:33:02.856Z",
                listOf("CS", "운동")
            ),
            StudyRecord(
                "청산별곡 정주행",
                "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                "2025-04-19T04:33:02.856Z",
                "2025-04-19T06:33:02.856Z",
                listOf("CS", "네트워크")
            ),
            StudyRecord(
                "청산별곡 정주행",
                "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                "2025-04-18T04:33:02.856Z",
                "2025-04-18T06:33:02.856Z",
                listOf("CS", "네트워크")
            ),
            StudyRecord(
                "청산별곡 정주행",
                "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                "2025-04-18T04:33:02.856Z",
                "2025-04-18T06:33:02.856Z",
                listOf("CS", "네트워크")
            ),
            StudyRecord(
                "청산별곡 정주행",
                "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                "2025-04-17T04:33:02.856Z",
                "2025-04-17T06:33:02.856Z",
                listOf("CS", "네트워크")
            ),
            StudyRecord(
                "청산별곡 정주행",
                "오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵오늘 공부한 내용은 얄리얄리 얄라셩 얄라리 얄라 준식식 빵빵빵 ",
                "2025-04-17T04:33:02.856Z",
                "2025-04-17T06:33:02.856Z",
                listOf("CS", "네트워크")
            ),
        )
        return PersonalContract.State(studyRecords = dummyRecords)
    }

    override fun handleEvent(event: PersonalContract.Event) {
        when (event) {
            // dropdown
            is PersonalContract.Event.OnDateScreenSelected -> {
                setState {
                    copy(
                        isTagScreen = false,
                        isDateScreen = true
                    )
                }
                filteringTodayRecord()
            }

            is PersonalContract.Event.OnTagScreenSelected -> {
                setState {
                    copy(
                        isTagScreen = true,
                        isDateScreen = false
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
        }
    }

    // calendar
    private fun filteringRecordByDate(date: String) {
        val filtered = uiState.value.studyRecords.filter {
            it.startTime.substring(0, 10) == date
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

        val filtered = uiState.value.studyRecords.filter {
            it.startTime.substring(0, 10) == today
        }

        setState {
            copy(
                selectedDate = today,
                selectedRecordsByDate = filtered,
            )
        }
    }


    //tag

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
                unSelectedTags = emptyList()
            )
        }
    }

    private fun moveFromTagsToSelectedTags() =
        setState { copy(selectedTags = uiState.value.tags, unSelectedTags = emptyList()) }

    private fun clearDialogTags() =
        setState { copy(selectedTags = emptyList(), unSelectedTags = emptyList()) }

    private fun deleteTag(tag: Tag) {
        val newTags = uiState.value.tags - tag
        setState { copy(tags = newTags) }
    }

    private fun selectTag(tag: Tag) {
        setState {
            copy(
                selectedTags = uiState.value.selectedTags + tag,
                unSelectedTags = uiState.value.unSelectedTags - tag
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

        val startsWithList = availableTags.filter { tag ->
            tag.koName.startsWith(query) || tag.enName.lowercase().startsWith(query.lowercase())
        }

        val kmpList = availableTags.filter { tag ->
            kmp(tag.koName, query) || kmp(tag.enName.lowercase(), query.lowercase())
        }

        val merged = buildList {
            addAll(startsWithList)
            addAll(kmpList.filterNot { it in startsWithList })
        }.take(10)

        setState { copy(unSelectedTags = merged) }
    }


    private fun kmp(text: String, pattern: String): Boolean {
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
