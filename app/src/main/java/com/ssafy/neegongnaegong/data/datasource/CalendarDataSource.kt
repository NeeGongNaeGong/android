package com.ssafy.neegongnaegong.data.datasource

import com.ssafy.neegongnaegong.data.model.calendar.request.CreatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.DeletePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.response.CreatePersonalScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.GetUserScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.UpdatePersonalScheduleResponse
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface CalendarDataSource {
    suspend fun getUserSchedules(
        month: YearMonth
    ): Flow<GetUserScheduleResponse>

    suspend fun createUserSchedule(
        request: CreatePersonalScheduleRequest
    ): Flow<CreatePersonalScheduleResponse>

    suspend fun updateUserSchedule(
        scheduleId: Long,
        request: UpdatePersonalScheduleRequest
    ): Flow<UpdatePersonalScheduleResponse>

    suspend fun deleteUserSchedule(
        scheduleId: Long,
        request: DeletePersonalScheduleRequest
    ): Flow<Unit>
}