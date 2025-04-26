package com.ssafy.neegongnaegong.data.model.calendar.response

import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleType
import java.time.LocalDateTime

data class ScheduleResponse(
    val type: ScheduleType,
    val id: Long,
    val title: String,
    val content: String?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val location: String?,
    val repeatRule: RepeatRuleResponse? = null,
) {
    fun toDomain(): Schedule {
        return Schedule(
            type = type,
            id = id,
            info = ScheduleInfo(
                title = title,
                content = content,
                startAt = startAt,
                endAt = endAt,
                isAllDay = endAt.second == 59, // TODO: 추후 수정
                location = location,
                repeatRule = repeatRule?.toDomain()
            ),
        )
    }
}
