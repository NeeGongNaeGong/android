package com.ssafy.neegongnaegong.data.model.calendar

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRule
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import java.time.LocalDate

data class RepeatRuleResponse(
    val id: Long,
    val repeatType: String,
    val repeatInterval: Int,
    val repeatDay: Int,
    val startDate: LocalDate,
    val endDate: LocalDate?,
) {
    fun toDomain(): RepeatRule {
        return RepeatRule(
            id = id,
            repeatType = RepeatType.valueOf(repeatType),
            repeatInterval = repeatInterval,
            repeatDay = repeatDay,
            startDate = startDate,
            endDate = endDate
        )
    }
}
