package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface CalendarRepository {
    suspend fun getUserSchedules(month: YearMonth): Flow<List<Schedule>>
    suspend fun createUserSchedule(schedule: ScheduleInfo): Flow<Schedule>
    suspend fun updateUserSchedule(id: Long, schedule: ScheduleInfo, type: UpdateType, date: LocalDate): Flow<Schedule>
    suspend fun deleteUserSchedule(id: Long, type: DeleteType, date: LocalDate): Flow<Unit>
}
