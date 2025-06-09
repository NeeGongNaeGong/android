package com.ssafy.neegongnaegong.presentation.group.user.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.usecase.user.SearchUserUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserUiModelMapper.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel
    @Inject
    constructor(
        private val searchUserUseCase: SearchUserUseCase,
    ) : BaseViewModel<UserSearchContract.Event, UserSearchContract.State, UserSearchContract.Effect>() {
        @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
        val searchUserList by lazy {
            uiState
                .map { it.searchKeyword }
                .debounce(300)
                .safeFlatMapLatest(errorContext = UserSearchContract.Error.GetUserListError) {
                    searchUserUseCase.invoke(
                        it,
                    )
                }.map { it.toUiModel() }
                .cachedIn(viewModelScope)
                .toViewModelState(PagingData.empty())
        }

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

        private fun handleOnTypingSearch(searchWord: String) {
            setState {
                copy(searchKeyword = searchWord)
            }
        }

        private fun handleOnClickSearch() {
        }
    }
