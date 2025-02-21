package com.ssafy.neegongnaegong.presentation.github

import com.ssafy.neegongnaegong.domain.model.GitHubRepo
import com.ssafy.neegongnaegong.presentation.base.UiEffect
import com.ssafy.neegongnaegong.presentation.base.UiEvent
import com.ssafy.neegongnaegong.presentation.base.UiState

class GitHubContract {

    sealed class Event : UiEvent {
        data class OnSearchClicked(val userName: String) : Event()
    }

    data class State(val githubState: GitHubState) : UiState

    sealed class GitHubState {
        data object Idle : GitHubState()
        data object Loading : GitHubState()
        data class Success(val repos: List<GitHubRepo>) : GitHubState()
        data class Error(val message: String) : GitHubState()
    }

    sealed class Effect : UiEffect {
        data object showRepos : Effect()
    }
}