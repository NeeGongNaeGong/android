package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.GitHubRepoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("users/{user}/repos")
    suspend fun getUserRepo(@Path("user") username: String): List<GitHubRepoResponse>
}