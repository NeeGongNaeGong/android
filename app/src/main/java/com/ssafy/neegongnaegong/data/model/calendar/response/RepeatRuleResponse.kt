package com.ssafy.neegongnaegong.data.model.calendar.response

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import java.time.LocalDate

data class RepeatRuleResponse(
    val id: Long,
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val endDate: LocalDate?,
)
