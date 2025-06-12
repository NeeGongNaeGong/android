package com.ssafy.neegongnaegong.data.datasource.network

import com.ssafy.neegongnaegong.data.model.calendar.request.CreatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.DeletePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.response.CreatePersonalScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.GetUserScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.ScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.UpdatePersonalScheduleResponse
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface NetworkCalendarDataSource {
    suspend fun getUserSchedules(month: YearMonth): Flow<GetUserScheduleResponse>

    suspend fun getPersonalSchedule(
        scheduleId: Long,
        date: LocalDate,
    ): Flow<ScheduleResponse>

    suspend fun createPersonalSchedule(request: CreatePersonalScheduleRequest): Flow<CreatePersonalScheduleResponse>

    suspend fun updatePersonalSchedule(
        scheduleId: Long,
        request: UpdatePersonalScheduleRequest,
    ): Flow<UpdatePersonalScheduleResponse>

    suspend fun deletePersonalSchedule(
        scheduleId: Long,
        request: DeletePersonalScheduleRequest,
    ): Flow<Unit>
}
