package com.ssafy.neegongnaegong.presentation.group.list.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetNoticeListUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetVoteListUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.main.ListContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.main.ListContract.Index
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
                            setEffect { Effect.NavigateToMakeNotice }
                        }

                        Index.Vote.index -> {
                            setEffect { Effect.NavigateToMakeVote }
                        }
                    }
                }

                // groupId인자 or startTab 인자가 제대로 들어오지 않은 경우
                ListContract.Event.InvalidAccess -> {
                    setEffect { Effect.NavigateToBackStack }
                }

                is ListContract.Event.OnClickNoticeItem -> {
                    setEffect { Effect.NavigateToNoticeDetailScreen(event.noticeId) }
                }

                is ListContract.Event.OnClickVoteItem -> {
                    setEffect { Effect.NavigateToVoteDetailScreen(event.voteId) }
                }
            }
        }

        val noticeListFlow: Flow<PagingData<NoticeHistoryInfo>>
        val voteListFlow: Flow<PagingData<VoteHistoryInfo>>

        init {
            val studyGroupId = savedStateHandle.toRoute<AppNavigation.Screen.Studies.SubTab.Main>().studyGroupId

            setState { copy(groupId = studyGroupId) }

            noticeListFlow =
                getNoticeListUseCase(studyGroupId).cachedIn(
                    viewModelScope,
                )

            voteListFlow =
                getVoteListUseCase(studyGroupId).cachedIn(
                    viewModelScope,
                )
        }
    }
