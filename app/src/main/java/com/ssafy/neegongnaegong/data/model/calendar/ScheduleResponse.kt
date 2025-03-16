package com.ssafy.neegongnaegong.data.model.calendar

import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import java.time.LocalDateTime

data class ScheduleResponse(
    val type: String,
    val id: Int,
    val title: String,
    val content: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val repeatRule: RepeatRuleResponse? = null,
) {
    fun toDomain(): Schedule {
        return Schedule(
            type = ScheduleType.valueOf(this.type),
            id = this.id,
            title = this.title,
            content = this.content,
            startDate = this.startDate,
            endDate = this.endDate,
            repeatRule = this.repeatRule?.toDomain()
        )
    }
}