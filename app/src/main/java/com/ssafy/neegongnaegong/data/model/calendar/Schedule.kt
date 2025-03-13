package com.ssafy.neegongnaegong.data.model.calendar

import java.time.LocalDateTime

data class Schedule(
    val type: ScheduleType,
    val id: Int,
    val title: String,
    val content: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val repeatRule: RepeatRule? = null,
)