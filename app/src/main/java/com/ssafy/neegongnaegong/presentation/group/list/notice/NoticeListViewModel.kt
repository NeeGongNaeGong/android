package com.ssafy.neegongnaegong.presentation.group.list.notice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetNoticeListUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeListContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeListContract.Event
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class NoticeListViewModel
    @Inject
    constructor(
        getNoticeListUseCase: GetNoticeListUseCase,
        savedStateHandle: SavedStateHandle,
    ) :
    BaseViewModel<Event, State, Effect>() {
        // 기본 선택된 탭은 공지 화면으로 설정
        override fun createInitialState(): State = State

        override fun handleEvent(event: Event) {
            when (event) {
                // Record Screen의 인자가 제대로 들어오지 않은 경우
                Event.InvalidAccess -> {
                    setEffect { Effect.NavigateToBackStack }
                }
            }
        }

        private val groupId: Long? = savedStateHandle["groupId"]
        val voteListFlow: Flow<PagingData<NoticeHistoryInfo>>

        init {
            if (groupId != null) {
                voteListFlow =
                    getNoticeListUseCase(groupId).cachedIn(
                        viewModelScope,
                    )
            } else {
                // studyLogFlow를 nullable하게 처리하고 싶지 않다보니 에러로 쓰지 않을 것임에도 초기화를 진행하게 되네요
                voteListFlow = flow {}
                showErrorMessage("잘못된 그룹입니다")
                setEvent(Event.InvalidAccess)
            }
        }
    }
