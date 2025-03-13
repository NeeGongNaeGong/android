package com.ssafy.neegongnaegong.data.model.calendar

import java.time.LocalDate

data class RepeatRule(
    val id: Long,
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val startDate: LocalDate,
    val endDate: LocalDate?,
)