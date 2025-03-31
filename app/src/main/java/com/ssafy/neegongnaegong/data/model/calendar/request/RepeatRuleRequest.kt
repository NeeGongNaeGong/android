package com.ssafy.neegongnaegong.data.model.calendar.request

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import java.time.LocalDate

open class RepeatRuleRequest(
    open val repeatType: RepeatType,
    open val repeatInterval: Int,
    open val repeatDay: Int,
    open val startDate: LocalDate,
    open val endDate: LocalDate? = null
)
