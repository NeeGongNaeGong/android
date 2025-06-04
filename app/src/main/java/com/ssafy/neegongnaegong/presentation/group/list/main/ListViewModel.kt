package com.ssafy.neegongnaegong.presentation.group.list.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetNoticeListUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetVoteListUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.main.ListContract.Index
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        getNoticeListUseCase: GetNoticeListUseCase,
        getVoteListUseCase: GetVoteListUseCase,
    ) :
    BaseViewModel<ListContract.Event, ListContract.State, ListContract.Effect>() {
        // 기본 선택된 탭은 공지 화면으로 설정
        override fun createInitialState(): ListContract.State = ListContract.State(-1)

        override fun handleEvent(event: ListContract.Event) {
            when (event) {
                is ListContract.Event.OnClickAddContent -> {
                    when (event.currentPage) {
                        Index.Notice.index -> {
                            TODO("공지 생성 화면으로 이동")
                        }

                        Index.Vote.index -> {
                            TODO("투표 생성 화면으로 이동")
                        }
                    }
                }

                // groupId인자 or startTab 인자가 제대로 들어오지 않은 경우
                ListContract.Event.InvalidAccess -> {
                    setEffect { ListContract.Effect.NavigateToBackStack }
                }

                is ListContract.Event.OnClickNoticeItem -> {
                    setEffect { ListContract.Effect.NavigateToNoticeDetailScreen(event.noticeId) }
                }

                is ListContract.Event.OnClickVoteItem -> {
                    setEffect { ListContract.Effect.NavigateToNoticeDetailScreen(event.voteId) }
                }
            }
        }

        val noticeListFlow: Flow<PagingData<NoticeHistoryInfo>>
        val voteListFlow: Flow<PagingData<VoteHistoryInfo>>

        init {
            val groupId: Long? = savedStateHandle["groupId"]
            val startTabIdx: Int? = savedStateHandle["startTab"]
            if (groupId != null && startTabIdx != null) {
                setState { copy(groupId = groupId) }

                noticeListFlow =
                    getNoticeListUseCase(groupId).cachedIn(
                        viewModelScope,
                    )

                voteListFlow =
                    getVoteListUseCase(groupId).cachedIn(
                        viewModelScope,
                    )
            } else {
                noticeListFlow = flow {}
                voteListFlow = flow {}
                showErrorMessage("잘못된 접근입니다")
                setEvent(ListContract.Event.InvalidAccess)
            }
        }
    }
