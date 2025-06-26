package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studies.request.CreateNoticeRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.CreateVoteRequest
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesApplicationsMembersResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesFeedsResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesMemberListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesWeeklyRankingsResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesResponse
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime

interface StudiesApi {
    @GET("/studies")
    suspend fun getStudies(): List<Studies>

    @POST("$PREFIX/{study-group-id}/posts/votes")
    suspend fun createVote(
        @Path("study-group-id") studyGroupId: Long,
        @Body requestBody: CreateVoteRequest,
    ): Result<ApiResponse<Unit>>

    @POST("$PREFIX/{study-group-id}/notices")
    suspend fun createNotice(
        @Path("study-group-id") studyId: Long,
        @Body requestBody: CreateNoticeRequest,
    ): Result<ApiResponse<Unit>>

    suspend fun getStudiesDel(): List<Studies> // TODO : 제거

    @GET("$PREFIX/list")
    suspend fun getStudiesList(
        @Query("cursor-created-at") cursorCreatedAt: String?,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<CursorSliceStudiesListResponse>>

    @POST(PREFIX)
    suspend fun createStudies(
        @Body request: CreateStudiesRequest,
    ): Result<ApiResponse<Unit>>

    @GET("$PREFIX/{study-group-id}")
    suspend fun getStudies(
        @Path("study-group-id") studyGroupId: Long,
    ): Result<ApiResponse<StudiesResponse>>

    @PUT("$PREFIX/{study-group-id}")
    suspend fun updateStudies(
        @Path("study-group-id") studyGroupId: Long,
        @Body request: UpdateStudiesRequest,
    ): Result<ApiResponse<Unit>>

    @DELETE("$PREFIX/{study-group-id}")
    suspend fun deleteStudies(
        @Path("study-group-id") studyGroupId: Long,
    ): Result<ApiResponse<Unit>>

    @POST("$PREFIX/{study-group-id}/applications")
    suspend fun applyStudies(
        @Path("study-group-id") studyGroupId: Long,
    ): Result<ApiResponse<Unit>>

    @DELETE("$PREFIX/{study-group-id}/applications")
    suspend fun cancelApplicationsStudies(
        @Path("study-group-id") studyGroupId: Long,
    ): Result<ApiResponse<Unit>>

    @GET("$PREFIX/{study-group-id}/users")
    suspend fun getStudiesMembers(
        @Path("study-group-id") studyGroupId: Long,
    ): Result<ApiResponse<GetStudiesMemberListResponse>>

    @GET("$PREFIX/{study-group-id}/applications")
    suspend fun getStudiesApplications(
        @Path("study-group-id") studyGroupId: Long,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<GetStudiesApplicationsMembersResponse>>

    @PATCH("$PREFIX/{study-group-id}/applications/{user-id}/approve")
    suspend fun patchApproveStudiesApplications(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
        @Query("notification-id") notificationId: Long?,
    ): Result<ApiResponse<Unit>>

    @PATCH("$PREFIX/{study-group-id}/applications/{user-id}/reject")
    suspend fun patchRejectStudiesApplications(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
        @Query("notification-id") notificationId: Long?,
    ): Result<ApiResponse<Unit>>

    @PUT("$PREFIX/{study-group-id}/users/{user-id}")
    suspend fun changeRoleStudiesMember(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
        @Query("changeRole") changeRole: String,
    ): Result<ApiResponse<Unit>>

    @DELETE("$PREFIX/{study-group-id}/users/{user-id}")
    suspend fun expelStudiesMember(
        @Path("study-group-id") studyGroupId: Long,
        @Path("user-id") userId: Long,
    ): Result<ApiResponse<Unit>>

    @GET("$PREFIX/{study-group-id}/feeds")
    suspend fun getStudiesFeeds(
        @Path("study-group-id") studyGroupId: Long,
        @Query("cursor-created-at") cursorCreatedAt: LocalDateTime?,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<GetStudiesFeedsResponse>>

    @GET("$PREFIX/{study-group-id}/members/weekly-rankings")
    suspend fun getStudiesWeeklyRankings(
        @Path("study-group-id") studyGroupId: Long,
        @Query("cursor-study-time") cursorStudyTime: Long?,
        @Query("cursor-user-id") cursorUserId: Long?,
        @Query("first-page-requested-at") firstPageRequestedAt: LocalDateTime?,
        @Query("size") size: Int,
    ): Result<ApiResponse<GetStudiesWeeklyRankingsResponse>>

    companion object {
        const val PREFIX = "/api/study-groups"
    }
}
