package com.ssafy.neegongnaegong.data.model.calendar.response

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRule
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import java.time.LocalDate

data class RepeatRuleResponse(
    val id: Long,
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val startDate: LocalDate,
    val endDate: LocalDate?,
) {
    fun toDomain(): RepeatRule {
        return RepeatRule(
            id = id,
            info = RepeatRuleInfo(
                repeatType = repeatType,
                repeatInterval = repeatInterval,
                repeatDay = repeatDay,
                startDate = startDate,
                endDate = endDate
            )
        )
    }
}
