package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.remote.GitHubApi
import com.ssafy.neegongnaegong.domain.model.GitHubRepo
import com.ssafy.neegongnaegong.domain.repository.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val api: GitHubApi
) : GitHubRepository {
    override suspend fun getUserRepos(username: String): List<GitHubRepo> {
        return api.getUserRepo(username).map { it.toDomain() }
    }
}