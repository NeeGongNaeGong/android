package com.ssafy.neegongnaegong.presentation.group.list.notice

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssafy.neegongnaegong.domain.usecase.studygroup.DeleteNoticeDetailUseCase
import com.ssafy.neegongnaegong.domain.usecase.studygroup.GetNoticeDetailUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Effect
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Effect.NavigateToBackStack
import com.ssafy.neegongnaegong.presentation.group.list.notice.NoticeDetailContract.Effect.NavigateToSubTab
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
        private val deleteNoticeDetailUseCase: DeleteNoticeDetailUseCase,
    ) :
    BaseViewModel<Event, State, Effect>() {
        override fun createInitialState(): State =
            State(
                writer = "",
                writerProfileImage = "",
                createdAt = "",
                content = "",
                showPopup = false,
            )

        override fun handleEvent(event: Event) {
            when (event) {
                Event.InvalidAccess -> setEffect { NavigateToBackStack }

                Event.OnClickPopBackStackButton -> setEffect { NavigateToBackStack }

                Event.OnDismissPopUp -> setState { copy(showPopup = false) }

                Event.OnTogglePopup -> setState { copy(showPopup = !showPopup) }

                Event.OnDeleteNotice -> {
                    viewModelScope.launch {
                        deleteNoticeDetailUseCase(
                            studyGroupId = groupId,
                            noticeId = noticeId,
                        ).safeCollect {
                            showSuccessMessage("공지를 삭제했습니다!")
                            // SubTab으로 돌아가기 전 PopUp 메뉴 숨기고 이동
                            // setState로 처리하지 않으니 Navigation 될 때 잠깐 살아있는 현상 있었음
                            setState { copy(showPopup = false) }
                            setEffect { NavigateToSubTab }
                        }
                    }
                }

                Event.OnEditNotice -> {
                    showWarningMessage("아직 추가되지 않은 기능이에요! 삭제를 이용해주세요")
                    setState { copy(showPopup = false) }
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
