package com.ssafy.neegongnaegong.data.model.calendar.request

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import java.time.LocalDateTime

data class CreatePersonalScheduleRequest(
    val title: String,
    val content: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val location: String? = null,
    val repeatRule: CreateRepeatRuleRequest? = null,
) {
    companion object {
        fun fromDomain(schedule: ScheduleInfo, repeatRule: RepeatRuleInfo? = null) =
            CreatePersonalScheduleRequest(
                title = schedule.title,
                content = schedule.content,
                startAt = schedule.startAt,
                endAt = schedule.endAt,
                location = schedule.location,
                repeatRule = if (repeatRule != null) CreateRepeatRuleRequest.fromDomain(repeatRule) else null
            )
    }
}
