package com.ssafy.neegongnaegong.domain.usecase.calendar

import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.Flow

class GetScheduleDetailUseCase(private val calendarRepository: CalendarRepository) {
    suspend operator fun invoke(scheduleId: Long): Flow<Schedule> {
        return calendarRepository.getScheduleDetail(scheduleId)
    }
}
