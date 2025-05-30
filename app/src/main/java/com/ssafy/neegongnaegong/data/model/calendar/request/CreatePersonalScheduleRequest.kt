package com.ssafy.neegongnaegong.data.model.calendar.request

import java.time.LocalDateTime

data class CreatePersonalScheduleRequest(
    val title: String,
    val content: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val location: String? = null,
    val repeatRule: CreateRepeatRuleRequest? = null,
)
