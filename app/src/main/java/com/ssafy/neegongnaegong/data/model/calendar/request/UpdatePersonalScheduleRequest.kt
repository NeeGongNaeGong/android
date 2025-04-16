package com.ssafy.neegongnaegong.data.model.calendar.request

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import java.time.LocalDate
import java.time.LocalDateTime

data class UpdatePersonalScheduleRequest(
    val type: UpdateType,
    val date: LocalDate,
    val title: String,
    val content: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val location: String? = null,
    val repeatRule: UpdateRepeatRuleRequest? = null,
) {
    companion object {
        fun fromDomain(type: UpdateType, date: LocalDate, schedule: ScheduleInfo, repeatRule: RepeatRuleInfo?) =
            UpdatePersonalScheduleRequest(
                type = type,
                date = date,
                title = schedule.title,
                content = schedule.content,
                startAt = schedule.startAt,
                endAt = schedule.endAt,
                location = schedule.location,
                repeatRule = repeatRule?.let { UpdateRepeatRuleRequest.fromDomain(it) }
            )
    }
}
