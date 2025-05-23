package com.ssafy.neegongnaegong.data.model.calendar.request

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
)
