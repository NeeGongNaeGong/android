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

    suspend fun createPersonalSchedule(
        request: CreatePersonalScheduleRequest
    ): Flow<CreatePersonalScheduleResponse>

    suspend fun updatePersonalSchedule(
        scheduleId: Long,
        request: UpdatePersonalScheduleRequest
    ): Flow<UpdatePersonalScheduleResponse>

    suspend fun deletePersonalSchedule(
        scheduleId: Long,
        request: DeletePersonalScheduleRequest
    ): Flow<Unit>
}