package com.ssafy.neegongnaegong.data.model.calendar.request

import java.time.LocalDateTime

open class ScheduleRequest(
    open val title: String,
    open val content: String,
    open val startDate: LocalDateTime,
    open val endDate: LocalDateTime,
    open val location: String,
)
