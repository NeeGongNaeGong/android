package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.notification.GetNotificationResponse
import com.ssafy.neegongnaegong.data.model.notification.NotificationPage
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationApi {
    @GET("api/notifications")
    suspend fun getNotifications(
        @Query("cursor-id") cursorId: Long?,
        @Query("size") size: Int,
    ): Result<ApiResponse<NotificationPage>>

    @GET("/api/notifications/{notification-id}")
    suspend fun getNotification(
        @Path("notification-id") notificationId: Long,
    ): Result<ApiResponse<GetNotificationResponse>>

    @DELETE("/api/notifications/{notification-id}")
    suspend fun deleteNotification(
        @Path("notification-id") notificationId: Long,
    ): Result<ApiResponse<Unit>>

    @DELETE("/api/notifications/all")
    suspend fun deleteAllNotifications(): Result<ApiResponse<Unit>>

    @PATCH("/api/notifications/{notification-id}/read")
    suspend fun readNotification(
        @Path("notification-id") notificationId: Long,
    ): Result<ApiResponse<Unit>>
}
