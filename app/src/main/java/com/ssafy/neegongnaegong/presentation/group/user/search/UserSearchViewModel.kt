package com.ssafy.neegongnaegong.presentation.group.user.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ssafy.neegongnaegong.domain.usecase.user.SearchUserUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import com.ssafy.neegongnaegong.presentation.base.ErrorContext
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserUiModel
import com.ssafy.neegongnaegong.presentation.group.user.search.model.UserUiModelMapper.toUiModel
import com.ssafy.neegongnaegong.presentation.util.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
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
        override fun handleException(
            e: Throwable,
            errorContext: ErrorContext,
            retry: () -> Unit,
        ) {
            val error = errorContext as? UserSearchContract.Error ?: return

            when (error) {
                is UserSearchContract.Error.GetUserListError -> {
                    showErrorMessage(
                        message = "검색을 하지 못했습니다 네트워크 환경확인 후 재시도 해주세요",
                        action = SnackbarManager.Action.retry { retry() },
                    )
                }
            }
        }

        @OptIn(FlowPreview::class)
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

                is UserSearchContract.Event.OnDeclareClick -> {
                    handleOnDeclareClick(event.user)
                }
            }
        }

        private fun handleOnTypingSearch(searchWord: String) {
            setState {
                copy(searchKeyword = searchWord)
            }
        }

        private fun handleOnDeclareClick(user: UserUiModel) {
        }
    }
