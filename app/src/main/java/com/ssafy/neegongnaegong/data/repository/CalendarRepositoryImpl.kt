package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.datasource.CalendarDataSource
import com.ssafy.neegongnaegong.data.model.calendar.request.CreatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.DeletePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdatePersonalScheduleRequest
import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
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
    private val dataSource: CalendarDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CalendarRepository {
    override suspend fun getUserSchedules(
        month: YearMonth
    ): Flow<List<Schedule>> = withContext(ioDispatcher) {
        dataSource.getUserSchedules(month).map { it.schedules.map { it.toDomain() } }
    }

    override suspend fun createUserSchedule(
        schedule: ScheduleInfo
    ): Flow<Schedule> = withContext(ioDispatcher) {
        dataSource.createUserSchedule(CreatePersonalScheduleRequest.fromDomain(schedule))
            .map { it.toDomain() }
    }

    override suspend fun updateUserSchedule(
        id: Long,
        schedule: ScheduleInfo,
        type: UpdateType,
        date: LocalDate
    ): Flow<Schedule> = withContext(ioDispatcher) {
        dataSource.updateUserSchedule(
            id,
            UpdatePersonalScheduleRequest.fromDomain(type, date, schedule)
        ).map { it.toDomain() }
    }

    override suspend fun deleteUserSchedule(
        id: Long,
        type: DeleteType,
        date: LocalDate
    ): Flow<Unit> = withContext(ioDispatcher) {
        dataSource.deleteUserSchedule(id, DeletePersonalScheduleRequest(type, date))
    }
}
