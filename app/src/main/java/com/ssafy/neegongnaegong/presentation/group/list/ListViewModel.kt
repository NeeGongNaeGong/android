package com.ssafy.neegongnaegong.presentation.group.list

import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO(나중에 되면, 투표 화면에서 클릭했을 때는 투표화면이 최초 선택된 탭으로 해야 하고, 공지화면이면 공지화면으로 해야 하므로,
//  런타임에 결정되야 해서 assistedFactory로 해야 할 거 같기도..?)

@HiltViewModel
class ListViewModel
    @Inject
    constructor() :
    BaseViewModel<ListContract.Event, ListContract.State, ListContract.Effect>() {
        // 기본 선택된 탭은 공지 화면으로 설정
        override fun createInitialState(): ListContract.State = ListContract.State(Index.Notice)

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

                is ListContract.Event.OnClickTab -> {
                    // 같은 탭이면 굳이 state 다시 만들어서 recomposition 하지 않도록
                    if (uiState.value.index != event.tab) {
                        setState { copy(index = event.tab) }
                    }
                }
            }
        }
    }
