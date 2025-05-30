package com.ssafy.neegongnaegong.data.model.calendar.request

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import java.time.LocalDate

data class CreateRepeatRuleRequest(
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val endDate: LocalDate? = null,
)
