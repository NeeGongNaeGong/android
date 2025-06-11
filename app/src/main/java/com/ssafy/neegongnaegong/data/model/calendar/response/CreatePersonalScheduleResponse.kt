package com.ssafy.neegongnaegong.data.model.calendar.response

import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import java.time.LocalDateTime

data class CreatePersonalScheduleResponse(
    val type: ScheduleType,
    val id: Long,
    val title: String,
    val content: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val location: String?,
    val repeatRule: RepeatRuleResponse? = null,
)
