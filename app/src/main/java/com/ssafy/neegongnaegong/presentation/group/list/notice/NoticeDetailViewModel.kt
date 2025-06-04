package com.ssafy.neegongnaegong.presentation.group.list.notice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetNoticeDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Event
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class NoticeDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        getNoticeDetailUseCase: GetNoticeDetailUseCase,
    ) :
    BaseViewModel<Event, State, Effect>() {
        override fun createInitialState(): State =
            State(
                writer = "",
                writerProfileImage = "",
                createdAt = "",
                content = "",
            )

        override fun handleEvent(event: Event) {
            when (event) {
                Event.InvalidAccess -> {
                    setEffect { Effect.NavigateToBackStack }
                }
            }
        }

        private val groupId: Long? = savedStateHandle["groupId"]
        private val noticeId: Long? = savedStateHandle["noticeId"]

        init {
            if (groupId != null && noticeId != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    getNoticeDetailUseCase(groupId, noticeId).safeCollect {
                        val outputFormatter = DateTimeFormatter.ofPattern("M월 d일 a h:mm")
                        val createdAt = it.createdAt.format(outputFormatter)

                        setState {
                            copy(
                                writer = it.writer.name,
                                writerProfileImage = it.writer.profileImg,
                                createdAt = createdAt,
                                content = it.content,
                            )
                        }
                    }
                }
            } else {
                showErrorMessage("잘못된 그룹입니다")
                setEvent(Event.InvalidAccess)
            }
        }
    }
