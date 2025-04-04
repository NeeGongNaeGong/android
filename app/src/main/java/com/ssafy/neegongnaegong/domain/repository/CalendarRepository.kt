package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface CalendarRepository {
    suspend fun getUserSchedules(month: YearMonth): Flow<List<Schedule>>
    suspend fun createPersonalSchedule(schedule: ScheduleInfo, repeatRule: RepeatRuleInfo?): Flow<Schedule>
    suspend fun updatePersonalSchedule(id: Long, schedule: ScheduleInfo, repeatRule: RepeatRuleInfo?, type: UpdateType, date: LocalDate): Flow<Schedule>
    suspend fun deletePersonalSchedule(id: Long, type: DeleteType, date: LocalDate): Flow<Unit>
}
