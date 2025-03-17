package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.data.model.ApiResponse
import com.ssafy.neegongnaegong.data.model.calendar.request.CreatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.DeletePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.response.CreatePersonalScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.GetUserScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.UpdatePersonalScheduleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.YearMonth

interface UserCalendarApi {
    @GET("/api/user/calendar/schedules")
    suspend fun getUserSchedules(
        @Query("month") month: YearMonth
    ): Response<ApiResponse<GetUserScheduleResponse>>

    @POST("/api/user/calendar/schedules")
    suspend fun createUserSchedule(
        @Body request: CreatePersonalScheduleRequest
    ): Response<ApiResponse<CreatePersonalScheduleResponse>>

    @PUT("/api/user/calendar/schedules/{scheduleId}")
    suspend fun updateUserSchedule(
        @Path("scheduleId") scheduleId: Long,
        @Body request: UpdatePersonalScheduleRequest
    ): Response<ApiResponse<UpdatePersonalScheduleResponse>>

    @DELETE("/api/user/calendar/schedules/{scheduleId}")
    suspend fun deleteUserSchedule(
        @Path("scheduleId") scheduleId: Long,
        @Body request: DeletePersonalScheduleRequest
    ): Response<ApiResponse<Unit>>
}
