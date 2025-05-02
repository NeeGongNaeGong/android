package com.ssafy.neegongnaegong.domain.usecase.calendar

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.Flow

class CreatePersonalSchedulesUseCase(private val calendarRepository: CalendarRepository) {

    suspend operator fun invoke(
        schedule: ScheduleInfo,
        repeatRule: RepeatRuleInfo? = null
    ): Flow<Schedule> {
        return calendarRepository.createPersonalSchedule(schedule, repeatRule)
    }
}
