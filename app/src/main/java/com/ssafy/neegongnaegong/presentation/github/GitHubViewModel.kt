package com.ssafy.neegongnaegong.presentation.github

import androidx.lifecycle.viewModelScope
import com.ssafy.neegongnaegong.domain.usecase.GetUserReposUseCase
import com.ssafy.neegongnaegong.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GitHubViewModel @Inject constructor(
    private val getUserReposUseCase: GetUserReposUseCase
) : BaseViewModel<GitHubContract.Event, GitHubContract.State, GitHubContract.Effect>() {

    override fun createInitialState(): GitHubContract.State {
        return GitHubContract.State(
            GitHubContract.GitHubState.Idle
        )
    }

    override fun handleEvent(event: GitHubContract.Event) {
        when (event) {
            is GitHubContract.Event.OnSearchClicked -> { fetchRepos(event.userName) }
        }
    }

    private fun fetchRepos(userName: String) {
        viewModelScope.launch {

            setState { copy(githubState = GitHubContract.GitHubState.Loading) }

            try {
                val repos = getUserReposUseCase(userName)
                setState { copy(githubState = GitHubContract.GitHubState.Success(repos)) }
                setEffect { GitHubContract.Effect.ShowRepos }
            } catch (e: Exception) {
                setState { copy(githubState = GitHubContract.GitHubState.Error(e.message ?: "네트워크 통신 실패")) }
            }
        }
    }

}