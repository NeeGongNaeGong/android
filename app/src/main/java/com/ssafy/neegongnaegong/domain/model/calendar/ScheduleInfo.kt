package com.ssafy.neegongnaegong.domain.model.calendar

import java.time.LocalDateTime

data class ScheduleInfo(
    val title: String,
    val content: String?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isAllDay: Boolean,
    val location: String? = null,
    val repeatRule: RepeatRule? = null,
) {
    companion object {
        fun empty() = ScheduleInfo(
            title = "",
            content = null,
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now(),
            isAllDay = false,
            location = null,
            repeatRule = null
        )
    }
}
