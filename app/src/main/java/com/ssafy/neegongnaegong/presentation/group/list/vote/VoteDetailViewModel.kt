package com.ssafy.neegongnaegong.presentation.group.list.vote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.CastVoteUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetVoteDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.State
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.VoteOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoteDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        getVoteDetailUseCase: GetVoteDetailUseCase,
        private val castVoteUseCase: CastVoteUseCase,
    ) :
    BaseViewModel<Event, State, Effect>() {
        override fun createInitialState(): State =
            State(
                userName = "",
                userProfileImg = "",
                progressTime = "",
                voteTitle = "",
                voteOptions = persistentListOf(),
                selected = persistentListOf(),
                voteItems = persistentListOf(),
                voteValues = persistentListOf(),
                editMode = false,
            )

        override fun handleEvent(event: Event) {
            when (event) {
                Event.InvalidAccess -> {
                    setEffect { Effect.NavigateToBackStack }
                }

                is Event.SelectOption -> {
                    val selectedItem =
                        StudyGroupVoteDetailInfo.VoteValue(
                            event.voteItemId,
                            event.voteItemName,
                        )

                    if (uiState.value.voteOptions.contains(VoteOptions.IS_MULTIPLE)) {
                        // 다중 선택일 때 선택한 옵션이 이미 selected에 있는지 확인해서 있으면 제거, 없으면 추가
                        setState {
                            copy(
                                selected =
                                    if (uiState.value.selected.contains(selectedItem)) {
                                        uiState.value.selected.remove(selectedItem)
                                    } else {
                                        uiState.value.selected.add(selectedItem)
                                    },
                            )
                        }
                    } else {
                        setState {
                            copy(
                                selected =
                                    if (uiState.value.selected.contains(selectedItem)) {
                                        persistentListOf()
                                    } else {
                                        persistentListOf(
                                            selectedItem,
                                        )
                                    },
                            )
                        }
                    }
                }

                is Event.CastVote -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        if (uiState.value.selected != uiState.value.voteValues) {
                            if (groupId != null && voteId != null) {
                                castVoteUseCase(
                                    groupId,
                                    voteId,
                                    uiState.value.selected.map { it.voteItemName },
                                ).safeCollect {
                                    setVoteState(it)
                                }
                            } else {
                                showErrorMessage("잘못된 그룹입니다")
                                setEvent(Event.InvalidAccess)
                            }
                        } else {
                            setState { copy(editMode = false) }
                        }
                    }
                }

                is Event.OnClickVotedPersonList -> {
                    setEffect { Effect.NavigateToVotedPersonList(event.title, event.votedMemberInfo) }
                }

                Event.ToggleEditMode -> setState { copy(editMode = !editMode) }
            }
        }

        private val groupId: Long? = savedStateHandle["groupId"]
        private val voteId: Long? = savedStateHandle["voteId"]

        init {
            if (groupId != null && voteId != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    getVoteDetailUseCase(voteId).safeCollect {
                        setVoteState(it)
                    }
                }
            } else {
                showErrorMessage("잘못된 그룹입니다")
                setEvent(Event.InvalidAccess)
            }
        }

        private fun setVoteState(voteDetailInfo: StudyGroupVoteDetailInfo) {
            setState {
                copy(
                    userName = voteDetailInfo.userName,
                    userProfileImg = voteDetailInfo.userProfileImg,
                    progressTime = voteDetailInfo.progressTime,
                    voteTitle = voteDetailInfo.voteTitle,
                    selected = voteDetailInfo.voteValues.toPersistentList(),
                    voteOptions =
                        voteDetailInfo.voteOptions.mapNotNull { option ->
                            VoteOptions.from(option)
                        }
                            .filter { it != VoteOptions.IS_OPEN && it != VoteOptions.IS_CHOSEN }
                            .toPersistentList(),
                    voteItems = voteDetailInfo.voteItems.toPersistentList(),
                    voteValues = voteDetailInfo.voteValues.toPersistentList(),
                    editMode = voteDetailInfo.voteValues.isEmpty(),
                )
            }
        }
    }
