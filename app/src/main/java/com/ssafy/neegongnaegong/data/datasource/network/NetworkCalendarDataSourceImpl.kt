package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.apiFlow
import com.ssafy.neegongnaegong.data.model.calendar.request.CreatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.DeletePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.response.CreatePersonalScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.GetUserScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.ScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.UpdatePersonalScheduleResponse
import com.ssafy.neegongnaegong.data.remote.UserCalendarApi
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class NetworkCalendarDataSourceImpl @Inject constructor(
    private val api: UserCalendarApi
) : NetworkCalendarDataSource {
    override suspend fun getUserSchedules(
        month: YearMonth
    ): Flow<GetUserScheduleResponse> = apiFlow {
        api.getUserSchedules(month)
    }

    override suspend fun getPersonalSchedule(
        scheduleId: Long
    ): Flow<ScheduleResponse> = safeApiCall {
        api.getPersonalSchedule(scheduleId)
    }.toFlow()

    override suspend fun createPersonalSchedule(
        request: CreatePersonalScheduleRequest
    ): Flow<CreatePersonalScheduleResponse> = apiFlow {
        api.createPersonalSchedule(request)
    }

    override suspend fun updatePersonalSchedule(
        scheduleId: Long,
        request: UpdatePersonalScheduleRequest
    ): Flow<UpdatePersonalScheduleResponse> = apiFlow {
        api.updatePersonalSchedule(scheduleId, request)
    }

    override suspend fun deletePersonalSchedule(
        scheduleId: Long,
        request: DeletePersonalScheduleRequest
    ): Flow<Unit> = apiFlow {
        api.deletePersonalSchedule(scheduleId, request)
    }
}
