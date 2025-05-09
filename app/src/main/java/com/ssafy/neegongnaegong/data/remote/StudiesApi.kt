package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudiesApi {
    @GET("/studies")
    suspend fun getStudies(): List<Studies>

    @POST("/api/studies/{studyId}/posts/votes")
    suspend fun createVote(@Path("studyId") studyId: Int, @Body requestBody: CreateVoteRequest): Result<ApiResponse<Unit>>
}
