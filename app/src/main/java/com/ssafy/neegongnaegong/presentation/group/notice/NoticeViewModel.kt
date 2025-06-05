package com.ssafy.neegongnaegong.presentation.group.notice

import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel
    @Inject
    constructor() : BaseViewModel<NoticeContract.Event, NoticeContract.State, NoticeContract.Effect>() {
        override fun createInitialState(): NoticeContract.State = NoticeContract.State("", "")

        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            if (errorContext is NoticeContract.Error) {
                when (errorContext) {
                    NoticeContract.Error.CreateNoticeError -> {
                        showErrorMessage(
                            e.message ?: "공지 생성에 실패했습니다",
                            SnackbarManager.Action.retry { retry() },
                        )
                    }
                }
            }
        }

        override fun handleEvent(event: NoticeContract.Event) {
            when (event) {
                is NoticeContract.Event.OnChangeContent -> {
                    setState { copy(content = event.content) }
                }

                is NoticeContract.Event.OnChangeTitle -> {
                    setState { copy(title = event.title) }
                }

                NoticeContract.Event.OnClickCompleteButton -> TODO()
            }
        }
    }
