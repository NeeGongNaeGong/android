package com.ssafy.neegongnaegong.domain.model.calendar

import java.time.LocalDateTime

data class ScheduleInfo(
    val title: String,
    val content: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val isAllDay: Boolean,
    val location: String? = null,
    val repeatRule: RepeatRule? = null,
) {
    companion object {
        fun empty() = ScheduleInfo(
            title = "",
            content = null,
            startAt = LocalDateTime.now(),
            endAt = LocalDateTime.now(),
            isAllDay = false,
            location = null,
            repeatRule = null
        )
    }
}
