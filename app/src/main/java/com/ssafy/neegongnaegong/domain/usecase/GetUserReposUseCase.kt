package com.ssafy.neegongnaegong.domain.usecase

import com.ssafy.neegongnaegong.domain.model.GitHubRepo
import com.ssafy.neegongnaegong.domain.repository.GitHubRepository
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GitHubRepository
){
    suspend operator fun invoke(username: String): List<GitHubRepo> {
        return repository.getUserRepos(username)
    }
}