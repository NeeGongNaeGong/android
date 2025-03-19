package com.ssafy.neegongnaegong.domain.model.calendar

data class Schedule(
    val type: ScheduleType,
    val id: Long,
    val info: ScheduleInfo,
)
