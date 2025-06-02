package com.ssafy.neegongnaegong.domain.model.calendar

import java.time.LocalDateTime
import java.time.LocalTime

data class ScheduleInfo(
    val title: String,
    val content: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val location: String? = null,
    val repeatRule: RepeatRule? = null,
) {
    companion object {
        fun empty() = ScheduleInfo(
            title = "",
            content = null,
            startAt = LocalDateTime.now(),
            endAt = LocalDateTime.now(),
            location = null,
            repeatRule = null
        )
    }

    val isAllDay: Boolean
        get() = startAt.toLocalTime() == LocalTime.MIN
                && endAt.toLocalTime() == LocalTime.of(23, 59, 59)
}
