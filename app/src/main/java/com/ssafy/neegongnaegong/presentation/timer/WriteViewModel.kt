package com.ssafy.neegongnaegong.presentation.timer

import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WriteViewModel @Inject constructor() :
    BaseViewModel<WriteContract.Event, WriteContract.State, WriteContract.Effect>() {

    override fun createInitialState(): WriteContract.State {
        return WriteContract.State()
    }

    override fun handleEvent(event: WriteContract.Event) {
        when (event) {
            is WriteContract.Event.OnTitleChanged -> {

            }

            is WriteContract.Event.OnContentChanged -> {

            }

            WriteContract.Event.OnCancelClicked -> {

            }
            WriteContract.Event.OnConfirmClicked -> {

            }
            WriteContract.Event.OnTagEraseClicked -> {

            }
            WriteContract.Event.OnTagPlusClicked -> {

            }
        }
    }
}
