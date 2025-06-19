package com.ssafy.neegongnaegong.presentation.group.list.vote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.AddNewVoteOptionUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.CastVoteUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.DeleteVoteDetailUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetVoteDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect.NavigateToBackStack
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect.NavigateToSubTab
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Effect.NavigateToVotedPersonList
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.CastVote
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.InvalidAccess
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnCancelAddOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnChangeNewOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnClickAddOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnClickPopBackStackButton
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnClickVotedPersonList
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnConfirmAddOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnDeleteVote
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnDismissPopUp
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnEditVote
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.OnTogglePopup
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.SelectOption
import com.ssafy.neegongnaegong.presentation.group.list.vote.VoteDetailContract.Event.ToggleCastMode
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
        private val deleteVoteDetailUseCase: DeleteVoteDetailUseCase,
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
                showPopup = false,
            )

        override fun handleEvent(event: Event) {
            when (event) {
                InvalidAccess -> {
                    setEffect { NavigateToBackStack }
                }

                is SelectOption -> {
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

                is CastVote -> {
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

                is OnClickVotedPersonList -> {
                    setEffect { NavigateToVotedPersonList(event.title, event.votedMemberInfo) }
                }

                ToggleCastMode -> setState { copy(castMode = !castMode) }

                OnClickAddOption -> setState { copy(addOptionMode = true) }

                is OnChangeNewOption -> setState { copy(newOption = event.optionTitle) }

                OnCancelAddOption -> setState { copy(addOptionMode = false, newOption = "") }

                OnConfirmAddOption -> {
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

                OnClickPopBackStackButton -> setEffect { NavigateToBackStack }
                OnDeleteVote ->
                    viewModelScope.launch {
                        deleteVoteDetailUseCase(
                            studyGroupId = groupId,
                            voteId = voteId,
                        ).safeCollect {
                            showSuccessMessage("투표를 삭제했습니다!")
                            // SubTab으로 돌아가기 전 PopUp 메뉴 숨기고 이동
                            // setState로 처리하지 않으니 Navigation 될 때 잠깐 살아있는 현상 있었음
                            setState { copy(showPopup = false) }
                            setEffect { NavigateToSubTab }
                        }
                    }

                OnDismissPopUp -> setState { copy(showPopup = false) }
                OnEditVote -> {
                    showWarningMessage("아직 추가되지 않은 기능이에요! 삭제를 이용해주세요")
                    setState { copy(showPopup = false) }
                }

                OnTogglePopup -> setState { copy(showPopup = !showPopup) }
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
