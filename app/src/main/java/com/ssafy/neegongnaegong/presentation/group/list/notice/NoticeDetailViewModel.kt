package com.ssafy.neegongnaegong.presentation.group.list.notice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetNoticeDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Event
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.State
import com.ssafy.neegongnaegong.presentation.navigation.AppNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
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

        private val groupId: Long =
            savedStateHandle.toRoute<AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail>().groupId
        private val noticeId: Long =
            savedStateHandle.toRoute<AppNavigation.Screen.Studies.SubTab.Screen.NoticeDetail>().noticeId

        init {
            viewModelScope.launch {
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
        }
    }
