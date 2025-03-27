package com.ssafy.neegongnaegong.domain.model.calendar

import java.time.LocalDate

data class RepeatRuleInfo(
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val endDate: LocalDate?,
)
