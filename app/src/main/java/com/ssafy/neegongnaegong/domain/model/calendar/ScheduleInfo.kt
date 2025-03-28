package com.ssafy.neegongnaegong.domain.model.calendar

import java.time.LocalDateTime

data class ScheduleInfo(
    val title: String,
    val content: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val location: String? = null,
    val repeatRule: RepeatRule? = null,
)
