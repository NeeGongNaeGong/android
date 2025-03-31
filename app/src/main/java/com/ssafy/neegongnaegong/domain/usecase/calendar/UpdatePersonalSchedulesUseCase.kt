package com.ssafy.neegongnaegong.domain.usecase.calendar

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import com.ssafy.neegongnaegong.domain.repository.CalendarRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class UpdatePersonalSchedulesUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(
        id: Long,
        schedule: ScheduleInfo,
        repeatRule: RepeatRuleInfo?,
        type: UpdateType,
        date: LocalDate
    ): Flow<Schedule> {
        return calendarRepository.updatePersonalSchedule(id, schedule, repeatRule, type, date)
    }
}
