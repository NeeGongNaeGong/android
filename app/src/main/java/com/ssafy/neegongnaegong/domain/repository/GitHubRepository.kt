package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.GitHubRepo

interface GitHubRepository {

    suspend fun getUserRepos(username: String): List<GitHubRepo>
}
