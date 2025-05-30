package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.studies.request.CreateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.request.UpdateStudiesRequest
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesResponse
import com.ssafy.neegongnaegong.domain.model.studies.Studies
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface StudiesApi {
    @GET("/studies")
    suspend fun getStudiesDel(): List<Studies> // TODO : 제거

    @GET("/api/study-groups/list")
    suspend fun getStudiesList(
        @Query("cursor-created-at") cursorCreatedAt: String?,
        @Query("cursorId") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<CursorSliceStudiesListResponse>>

    @POST("/api/study-groups")
    suspend fun createStudies(
        @Body request: CreateStudiesRequest,
    ): Result<ApiResponse<Unit>>

    @GET("/api/study-groups/{studyGroupId}")
    suspend fun getStudies(
        @Path("studyGroupId") studyGroupId: Long,
    ): Result<ApiResponse<StudiesResponse>>

    @PUT("/api/study-groups/{studyGroupId}")
    suspend fun updateStudies(
        @Path("studyGroupId") studyGroupId: Long,
        @Body request: UpdateStudiesRequest,
    ): Result<ApiResponse<Unit>>

    @DELETE("/api/study-groups/{studyGroupId}")
    suspend fun deleteStudies(
        @Path("studyGroupId") studyGroupId: Int,
    ): Result<ApiResponse<Unit>>

    @POST("/api/study-groups/{studyGroupId}/applications")
    suspend fun applyStudies(
        @Path("studyGroupId") studyGroupId: Int,
    ): Result<ApiResponse<Unit>>

    @DELETE("/api/study-groups/{studyGroupId}/applications")
    suspend fun cancelApplicationsStudies(
        @Path("studyGroupId") studyGroupId: Int,
    ): Result<ApiResponse<Unit>>
}
