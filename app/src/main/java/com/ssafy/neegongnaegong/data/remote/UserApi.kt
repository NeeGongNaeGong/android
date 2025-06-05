package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.user.request.UpdateFcmTokenRequest
import com.ssafy.neegongnaegong.data.model.user.request.UpdateUserRequest
import com.ssafy.neegongnaegong.data.model.user.response.UserDetailResponse
import com.ssafy.neegongnaegong.data.model.user.response.UserPage
import com.ssafy.neegongnaegong.data.model.user.response.ValidateNicknameResponse
import com.ssafy.neegongnaegong.domain.model.pagable.PageableData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("/api/users/{id}")
    suspend fun getUser(@Path("id") id: Long): Result<ApiResponse<UserDetailResponse>>

    @GET("/api/users")
    suspend fun getUsers(
        @Query("nickname") nickname: String,
        @Query("page") page: Long,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>
    ): Result<ApiResponse<PageableData<UserDetailResponse>>>

    @GET("/api/users/nickname/validate")
    suspend fun validateNickname(@Query("nickname") nickname: String): Result<ApiResponse<ValidateNicknameResponse>>

    @POST("/api/users")
    suspend fun updateUser(@Body request: UpdateUserRequest): Result<ApiResponse<Unit>>

    @PUT("/token/fcm/refresh")
    suspend fun updateFcmToken(@Body request: UpdateFcmTokenRequest): Result<ApiResponse<Unit>>

    @GET("/api/users/list")
    suspend fun findUsers(
        @Query("cursor-time") time: String?,
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
        @Query("user-name") userName: String,
    ): Result<ApiResponse<UserPage>>
}
