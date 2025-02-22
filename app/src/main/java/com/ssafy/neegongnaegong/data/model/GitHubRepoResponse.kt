package com.ssafy.neegongnaegong.data.model

import com.ssafy.neegongnaegong.domain.model.GitHubRepo

data class GitHubRepoResponse(
    val id: Int,
    val name: String,
    val description: String?,
    val url: String?
){

    fun toDomain() = GitHubRepo(
        id = id,
        name = name,
        description = description ?: "No description",
        url = url
    )
}
