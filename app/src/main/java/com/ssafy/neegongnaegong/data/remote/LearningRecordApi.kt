package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CursorSliceResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.DeleteLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface LearningRecordApi {
    @PUT("/api/records/{learning-record-id}")
    suspend fun updateLearningRecord(
        @Path("learning-record-id") learningRecordId: Long,
        @Body request: UpdateLearningRecordRequest,
    ): Result<ApiResponse<Unit>>

    @DELETE("api/records/{learning-record-id}")
    suspend fun deleteLearningRecord(
        @Path("learning-record-id") learningRecordId: Long,
    ): Result<ApiResponse<DeleteLearningRecordResponse>>

    @GET("/api/records/{learning-record-id}")
    suspend fun getLearningRecord(
        @Path("learning-record-id") learningRecordId: Long,
    ): Result<ApiResponse<GetLearningRecordResponse>>

    @POST("/api/records")
    suspend fun createLearningRecord(
        @Body request: CreateLearningRecordRequest,
    ): Result<ApiResponse<Long>>

    @GET("/api/records/list")
    suspend fun getLearningRecordList(
        @Query("tag") tag: List<Long>?,
        @Query("target-date") targetDate: String?,
        @Query("cursor-created-at") cursorCreatedAt: String?,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<CursorSliceResponse>>
}
