package com.ssafy.neegongnaegong.presentation.group.list.main

import androidx.lifecycle.SavedStateHandle
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.navigation.Index
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.toImmutableList
import javax.inject.Inject

// TODO(나중에 되면, 투표 화면에서 클릭했을 때는 투표화면이 최초 선택된 탭으로 해야 하고, 공지화면이면 공지화면으로 해야 하므로,
//  런타임에 결정되야 해서 assistedFactory로 해야 할 거 같기도..?)

@HiltViewModel
class ListViewModel
    @Inject
    constructor(savedStateHandle: SavedStateHandle) :
    BaseViewModel<ListContract.Event, ListContract.State, ListContract.Effect>() {
        // 기본 선택된 탭은 공지 화면으로 설정
        override fun createInitialState(): ListContract.State = ListContract.State(-1, Index.Notice)

        override fun handleEvent(event: ListContract.Event) {
            when (event) {
                ListContract.Event.OnClickAddContent -> {
                    when (uiState.value.index) {
                        Index.Notice -> {
                            TODO("공지 생성 화면으로 이동")
                        }

                        Index.Vote -> {
                            TODO("투표 생성 화면으로 이동")
                        }
                    }
                }

                is ListContract.Event.OnTabChanged -> {
                    setEffect {
                        when (event.tab) {
                            Index.Notice -> ListContract.Effect.NavigateToNoticeScreen
                            Index.Vote -> ListContract.Effect.NavigateToVoteScreen
                        }
                    }
                }

                // Record Screen의 인자가 제대로 들어오지 않은 경우
                ListContract.Event.InvalidAccess -> {
                    setEffect { ListContract.Effect.NavigateToBackStack }
                }

                is ListContract.Event.OnSyncTab -> {
                    setState { copy(index = event.tab) }
                }
            }
        }

        init {
            val groupId: Long? = savedStateHandle["groupId"]
            val startTabIdx: Int? = savedStateHandle["startTab"]
            if (groupId != null && startTabIdx != null) {
                val index = Index.entries.toImmutableList().first { it.index == startTabIdx }
                setState { copy(groupId = groupId, index = index) }
            } else {
                showErrorMessage("잘못된 접근입니다")
                setEvent(ListContract.Event.InvalidAccess)
            }
        }
    }
