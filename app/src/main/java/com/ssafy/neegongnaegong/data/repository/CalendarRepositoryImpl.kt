package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.network.NetworkCalendarDataSource
import com.ssafy.neegongnaegong.data.model.calendar.request.CreatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.DeletePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdatePersonalScheduleRequest
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import com.ssafy.neegongnaegong.module.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val dataSource: NetworkCalendarDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CalendarRepository {
    override suspend fun getUserSchedules(
        month: YearMonth
    ): Flow<List<Schedule>> = withContext(ioDispatcher) {
        dataSource.getUserSchedules(month).map { it.schedules.map { it.toDomain() } }
    }

    override suspend fun getScheduleDetail(
        id: Long
    ): Flow<Schedule> = withContext(ioDispatcher) {
        dataSource.getPersonalSchedule(id).map { it.toDomain() }
    }

    override suspend fun createPersonalSchedule(
        schedule: ScheduleInfo,
        repeatRule: RepeatRuleInfo?
    ): Flow<Schedule> = withContext(ioDispatcher) {
        dataSource.createPersonalSchedule(CreatePersonalScheduleRequest.fromDomain(schedule, repeatRule))
            .map { it.toDomain() }
    }

    override suspend fun updatePersonalSchedule(
        id: Long,
        schedule: ScheduleInfo,
        repeatRule: RepeatRuleInfo?,
        type: UpdateType,
        date: LocalDate
    ): Flow<Schedule> = withContext(ioDispatcher) {
        dataSource.updatePersonalSchedule(
            id,
            UpdatePersonalScheduleRequest.fromDomain(type, date, schedule, repeatRule)
        ).map { it.toDomain() }
    }

    override suspend fun deletePersonalSchedule(
        id: Long,
        type: DeleteType,
        date: LocalDate
    ): Flow<Unit> = withContext(ioDispatcher) {
        dataSource.deletePersonalSchedule(id, DeletePersonalScheduleRequest(type, date))
    }
}
