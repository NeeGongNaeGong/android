package com.ssafy.neegongnaegong.presentation.group.list

import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel
    @Inject
    constructor() :
    BaseViewModel<ListContract.Event, ListContract.State, ListContract.Effect>() {
        override fun createInitialState(): ListContract.State = ListContract.State(0)

        override fun handleEvent(event: ListContract.Event) {
            TODO("이벤트를 처리하는 곳")
        }
    }
