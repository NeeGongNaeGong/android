package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.domain.model.studies.Category
import com.ssafy.neegongnaegong.domain.model.studies.Tag
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("/api/categories")
    suspend fun getCategories(): Result<ApiResponse<List<Category>>>

    @GET("/api/categories/{categoryId}/tags")
    suspend fun getTags(
        @Path("categoryId") categoryId: Long,
    ): Result<ApiResponse<List<Tag>>>
}
