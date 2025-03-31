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
                setState { copy(title = event.title) }
            }

            is WriteContract.Event.OnContentChanged -> {
                setState { copy(content = event.content) }
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
