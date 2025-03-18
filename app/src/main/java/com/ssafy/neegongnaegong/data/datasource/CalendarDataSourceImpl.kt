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
import javax.inject.Inject

class CalendarDataSourceImpl @Inject constructor(
    private val api: UserCalendarApi
) : CalendarDataSource {
    override suspend fun getUserSchedules(
        month: YearMonth
    ): Flow<GetUserScheduleResponse> = safeApiCall {
        api.getUserSchedules(month)
    }.toFlow()

    override suspend fun createPersonalSchedule(
        request: CreatePersonalScheduleRequest
    ): Flow<CreatePersonalScheduleResponse> = safeApiCall {
        api.createPersonalSchedule(request)
    }.toFlow()

    override suspend fun updatePersonalSchedule(
        scheduleId: Long,
        request: UpdatePersonalScheduleRequest
    ): Flow<UpdatePersonalScheduleResponse> = safeApiCall {
        api.updatePersonalSchedule(scheduleId, request)
    }.toFlow()

    override suspend fun deletePersonalSchedule(
        scheduleId: Long,
        request: DeletePersonalScheduleRequest
    ): Flow<Unit> = safeApiCall {
        api.deletePersonalSchedule(scheduleId, request)
    }.toFlow()
}