package com.ssafy.neegongnaegong.data.model.calendar.request

import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import java.time.LocalDate
import java.time.LocalDateTime

data class UpdatePersonalScheduleRequest(
    val type: UpdateType,
    val date: LocalDate,
    val title: String,
    val content: String?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val location: String? = null,
    val repeatRule: UpdateRepeatRuleRequest? = null,
) {
    companion object {
        fun fromDomain(type: UpdateType, date: LocalDate, schedule: ScheduleInfo) =
            UpdatePersonalScheduleRequest(
                type = type,
                date = date,
                title = schedule.title,
                content = schedule.content,
                startDate = schedule.startDate,
                endDate = schedule.endDate,
                location = schedule.location,
                repeatRule = schedule.repeatRule?.let { UpdateRepeatRuleRequest.fromDomain(it.info) }
            )
    }
}