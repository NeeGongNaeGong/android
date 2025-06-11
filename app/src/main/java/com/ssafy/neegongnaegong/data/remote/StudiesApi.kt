package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studies.request.CreateNoticeRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudiesApi {
    @GET("/studies")
    suspend fun getStudies(): List<Studies>

    @POST("/api/study-groups/{study-group-id}/posts/votes")
    suspend fun createVote(
        @Path("study-group-id") studyGroupId: Long,
        @Body requestBody: CreateVoteRequest,
    ): Result<ApiResponse<Unit>>

    @POST("/api/study-groups/{study-group-id}/notices")
    suspend fun createNotice(
        @Path("study-group-id") studyId: Long,
        @Body requestBody: CreateNoticeRequest,
    ): Result<ApiResponse<Unit>>
}
