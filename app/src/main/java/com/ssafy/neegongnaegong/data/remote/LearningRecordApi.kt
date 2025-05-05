package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.request.CreateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.GetLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.request.UpdateLearningRecordRequest
import com.ssafy.neegongnaegong.data.model.learningrecord.response.CreateLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.DeleteLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse
import com.ssafy.neegongnaegong.data.model.learningrecord.response.UpdateLearningRecordResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface LearningRecordApi {
    @PUT("/api/records/{learningRecordId}")
    suspend fun updateLearningRecord(
        @Path("learningRecordId") learningRecordId: Long,
        @Body request: UpdateLearningRecordRequest,
    ): Result<ApiResponse<UpdateLearningRecordResponse>>

    @DELETE("api/records/{learningRecordId}")
    suspend fun deleteLearningRecord(
        @Query("learningRecordId") learningRecordId: Long,
    ): Result<ApiResponse<DeleteLearningRecordResponse>>

    @GET("/api/records")
    suspend fun getLearningRecord(request: GetLearningRecordRequest): Result<ApiResponse<GetLearningRecordResponse>>

    @POST("/api/records")
    suspend fun createLearningRecord(request: CreateLearningRecordRequest): Result<ApiResponse<CreateLearningRecordResponse>>
}
