package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.auth.request.LoginRequest
import com.ssafy.neegongnaegong.data.model.auth.request.RegisterRequest
import com.ssafy.neegongnaegong.data.model.auth.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/auth/google/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<LoginResponse>>

    @POST("/api/auth/goggle/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<Long>>

    @POST("/api/auth/token/refresh")
    suspend fun reissue(@Body refreshToken: String): Response<ApiResponse<LoginResponse>>
}
