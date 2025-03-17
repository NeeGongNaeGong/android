package com.ssafy.neegongnaegong.data.datasource

import com.ssafy.neegongnaegong.data.model.calendar.request.CreatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.DeletePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.response.CreatePersonalScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.GetUserScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.UpdatePersonalScheduleResponse
import com.ssafy.neegongnaegong.data.model.safeApiCall
import com.ssafy.neegongnaegong.data.model.toFlow
import com.ssafy.neegongnaegong.data.remote.UserCalendarApi
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

class CalendarDataSourceImpl(
    private val api: UserCalendarApi
) : CalendarDataSource {
    override suspend fun getUserSchedules(
        month: YearMonth
    ): Flow<GetUserScheduleResponse> = safeApiCall {
        api.getUserSchedules(month)
    }.toFlow()

    override suspend fun createUserSchedule(
        request: CreatePersonalScheduleRequest
    ): Flow<CreatePersonalScheduleResponse> = safeApiCall {
        api.createUserSchedule(request)
    }.toFlow()

    override suspend fun updateUserSchedule(
        scheduleId: Long,
        request: UpdatePersonalScheduleRequest
    ): Flow<UpdatePersonalScheduleResponse> = safeApiCall {
        api.updateUserSchedule(scheduleId, request)
    }.toFlow()

    override suspend fun deleteUserSchedule(
        scheduleId: Long,
        request: DeletePersonalScheduleRequest
    ): Flow<Unit> = safeApiCall {
        api.deleteUserSchedule(scheduleId, request)
    }.toFlow()
}