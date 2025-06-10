package com.ssafy.neegongnaegong.presentation.group.list.vote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.AddNewVoteOptionUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.CastVoteUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetVoteDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.State
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.VoteOptions
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoteDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        getVoteDetailUseCase: GetVoteDetailUseCase,
        private val addNewVoteOptionUseCase: AddNewVoteOptionUseCase,
        private val castVoteUseCase: CastVoteUseCase,
    ) :
    BaseViewModel<Event, State, Effect>() {
        override fun createInitialState(): State =
            State(
                userName = "",
                userProfileImg = "",
                progressTime = "",
                voteTitle = "",
                state = false,
                voteOptions = persistentListOf(),
                selected = persistentListOf(),
                voteItems = persistentListOf(),
                voteValues = persistentListOf(),
                castMode = false,
                addOptionMode = false,
                newOption = "",
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
                    viewModelScope.launch {
                        if (uiState.value.selected != uiState.value.voteValues) {
                            castVoteUseCase(
                                groupId,
                                voteId,
                                uiState.value.selected.map { it.voteItemName },
                            ).safeCollect {
                                setVoteState(it)
                            }
                        } else {
                            setState { copy(castMode = false) }
                        }
                    }
                }

                is Event.OnClickVotedPersonList -> {
                    setEffect { Effect.NavigateToVotedPersonList(event.title, event.votedMemberInfo) }
                }

                Event.ToggleCastMode -> setState { copy(castMode = !castMode) }

                Event.OnClickAddOption -> setState { copy(addOptionMode = true) }

                is Event.OnChangeNewOption -> setState { copy(newOption = event.optionTitle) }

                Event.OnCancelAddOption -> setState { copy(addOptionMode = false, newOption = "") }

                Event.OnConfirmAddOption -> {
                    viewModelScope.launch {
                        addNewVoteOptionUseCase(
                            groupId,
                            voteId,
                            uiState.value.newOption,
                        ).safeCollect {
                            setVoteState(it)
                        }
                    }
                }
            }
        }

        private val groupId: Long =
            savedStateHandle.toRoute<AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail>().groupId
        private val voteId: Long =
            savedStateHandle.toRoute<AppNavigation.Screen.Studies.SubTab.Screen.VoteDetail>().voteId

        init {
            viewModelScope.launch {
                getVoteDetailUseCase(voteId).safeCollect {
                    setVoteState(it)
                }
            }
        }

        private fun setVoteState(voteDetailInfo: StudyGroupVoteDetailInfo) {
            setState {
                copy(
                    userName = voteDetailInfo.userName,
                    userProfileImg = voteDetailInfo.userProfileImg,
                    progressTime = voteDetailInfo.progressTime,
                    voteTitle = voteDetailInfo.voteTitle,
                    state = voteDetailInfo.state,
                    selected = voteDetailInfo.voteValues.toPersistentList(),
                    voteOptions =
                        voteDetailInfo.voteOptions.mapNotNull { option ->
                            VoteOptions.from(option)
                        }
                            .filter { it != VoteOptions.IS_OPEN }
                            .toPersistentList(),
                    voteItems = voteDetailInfo.voteItems.toPersistentList(),
                    voteValues = voteDetailInfo.voteValues.toPersistentList(),
                    castMode = voteDetailInfo.voteValues.isEmpty(),
                    addOptionMode = false,
                    newOption = "",
                )
            }
        }
    }
