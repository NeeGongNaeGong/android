package com.ssafy.neegongnaegong.domain.usecase.calendar

import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetScheduleDetailUseCase(private val calendarRepository: CalendarRepository) {
    suspend operator fun invoke(scheduleId: Long, date: LocalDate): Flow<Schedule> {
        return calendarRepository.getScheduleDetail(scheduleId, date)
    }
}
