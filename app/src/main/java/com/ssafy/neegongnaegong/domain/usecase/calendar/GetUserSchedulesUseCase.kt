package com.ssafy.neegongnaegong.domain.usecase.calendar

import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

class GetUserSchedulesUseCase(private val calendarRepository: CalendarRepository) {

    suspend operator fun invoke(month: YearMonth): Flow<List<Schedule>> {
        return calendarRepository.getUserSchedules(month)
    }
}
