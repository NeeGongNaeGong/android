package com.ssafy.neegongnaegong.presentation.group.list.vote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetVoteListUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// TODO(나중에 되면, 투표 화면에서 클릭했을 때는 투표화면이 최초 선택된 탭으로 해야 하고, 공지화면이면 공지화면으로 해야 하므로,
//  런타임에 결정되야 해서 assistedFactory로 해야 할 거 같기도..?)

@HiltViewModel
class VoteViewModel
    @Inject
    constructor(
        getVoteListUseCase: GetVoteListUseCase,
        savedStateHandle: SavedStateHandle,
    ) :
    BaseViewModel<VoteContract.Event, VoteContract.State, VoteContract.Effect>() {
        // 기본 선택된 탭은 공지 화면으로 설정
        override fun createInitialState(): VoteContract.State = VoteContract.State

        override fun handleEvent(event: VoteContract.Event) {
            when (event) {
                // Record Screen의 인자가 제대로 들어오지 않은 경우
                VoteContract.Event.InvalidAccess -> {
                    setEffect { VoteContract.Effect.NavigateToBackStack }
                }
            }
        }

        private val groupId: Long? = savedStateHandle["groupId"]
        val voteListFlow: Flow<PagingData<VoteHistoryInfo>>

        init {
            if (groupId != null) {
                voteListFlow =
                    getVoteListUseCase(groupId).cachedIn(
                        viewModelScope,
                    )
            } else {
                // studyLogFlow를 nullable하게 처리하고 싶지 않다보니 에러로 쓰지 않을 것임에도 초기화를 진행하게 되네요
                voteListFlow = flow {}
                showErrorMessage("잘못된 그룹입니다")
                setEvent(VoteContract.Event.InvalidAccess)
            }
        }
    }
