package com.ssafy.neegongnaegong.presentation.group.user.search

import com.ssafy.neegongnaegong.domain.usecase.user.SearchUserUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel
    @Inject
    constructor(
        private val searchUserUseCase: SearchUserUseCase,
    ) : BaseViewModel<UserSearchContract.Event, UserSearchContract.State, UserSearchContract.Effect>() {
        override fun createInitialState(): UserSearchContract.State = UserSearchContract.State()

        override fun handleEvent(event: UserSearchContract.Event) {
            when (event) {
                is UserSearchContract.Event.OnTypingSearch -> {
                    handleOnTypingSearch(event.keyword)
                }

                is UserSearchContract.Event.OnClickSearch -> {
                    handleOnClickSearch()
                }
            }
        }

        private fun handleOnTypingSearch(searchWord: String) {}

        private fun handleOnClickSearch() {}
    }
