package com.ssafy.neegongnaegong.presentation.personal

import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor() :
    BaseViewModel<PersonalContract.Event, PersonalContract.State, PersonalContract.Effect>() {

    override fun createInitialState(): PersonalContract.State {
        return PersonalContract.State()
    }

    override fun handleEvent(event: PersonalContract.Event) {

    }


}
