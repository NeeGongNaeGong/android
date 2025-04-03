package com.ssafy.neegongnaegong.presentation.timer

import com.ssafy.neegongnaegong.domain.data.TagData
import com.ssafy.neegongnaegong.domain.model.write.Tag
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WriteViewModel @Inject constructor() :
    BaseViewModel<WriteContract.Event, WriteContract.State, WriteContract.Effect>() {

    override fun createInitialState(): WriteContract.State {
        return WriteContract.State()
    }

    override fun handleEvent(event: WriteContract.Event) {
        when (event) {
            // 글 작성
            is WriteContract.Event.OnTitleChanged -> {
                setState { copy(title = event.title) }
            }

            is WriteContract.Event.OnContentChanged -> {
                setState { copy(content = event.content) }
            }

            // 취소, 확인
            is WriteContract.Event.OnCancelClicked -> {

            }

            is WriteContract.Event.OnConfirmClicked -> {

            }

            // 태그 추가,삭제
            is WriteContract.Event.OnTagEraseClicked -> {
                deleteTag(event.tag)
            }

            is WriteContract.Event.OnTagSelected -> {
                selectTag(event.tag)
            }

            is WriteContract.Event.OnTagDeselected -> {
                deselectTag(event.tag)
            }

            is WriteContract.Event.OnSearchTextChanged -> {
                updateDialogTagsWithKmp(event.query)
            }

            is WriteContract.Event.OnTagPlusClicked -> {
                clearDialogTags()
                setState { copy(isDialogShow = true) }
            }

            is WriteContract.Event.OnDialogClose -> {
                clearDialogTags()
                setState { copy(isDialogShow = false) }
            }

            is WriteContract.Event.OnDialogConfirmClicked -> {
                if (checkTagSize()) {
                    setEffect { WriteContract.Effect.ShowTagLimitExceededToast }
                } else {
                    moveFromSelectedTagsToTags()
                    clearDialogTags()
                    setState { copy(isDialogShow = false) }
                }
            }

            is WriteContract.Event.OnDialogCancelClicked -> {
                setState { copy(isDialogShow = false) }
            }
        }
    }

    private fun clearDialogTags() =
        setState { copy(selectedTags = emptyList(), unSelectedTags = emptyList()) }

    private fun moveFromSelectedTagsToTags() {
        val newTags = uiState.value.tags + uiState.value.selectedTags
        setState {
            copy(
                tags = newTags,
                selectedTags = emptyList(),
                unSelectedTags = emptyList()
            )
        }
    }

    private fun checkTagSize() =
        ((uiState.value.tags.size + uiState.value.selectedTags.size) > MAX_TAG_LIMIT)


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

    private fun updateDialogTags(query: String) {

        val filtered = if (query.isBlank()) {
            emptyList()
        } else {
            TagData.tags
                .filterNot { tag ->
                    uiState.value.tags.contains(tag) || uiState.value.selectedTags.contains(
                        tag
                    )
                }
                .filter { tag ->
                    tag.koName.contains(query) || tag.enName.contains(
                        query,
                        ignoreCase = true
                    )
                }
                .take(10)
        }

        setState { copy(unSelectedTags = filtered) }
    }

    private fun updateDialogTagsWithKmp(query: String) {
        val filtered = if (query.isBlank()) {
            emptyList()
        } else {
            TagData.tags
                .filterNot { tag ->
                    uiState.value.tags.contains(tag) || uiState.value.selectedTags.contains(tag)
                }
                .filter { tag ->
                    kmp(tag.koName, query) || kmp(tag.enName.lowercase(), query.lowercase())
                }
                .take(10)
        }

        setState { copy(unSelectedTags = filtered) }
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


    companion object {
        const val MAX_TAG_LIMIT = 5
    }
}

