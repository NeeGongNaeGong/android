package com.ssafy.neegongnaegong.data.model.calendar.request

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import java.time.LocalDate

data class UpdateRepeatRuleRequest(
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val startDate: LocalDate,
    val endDate: LocalDate? = null
) {
    companion object {
        fun fromDomain(repeatRule: RepeatRuleInfo) = UpdateRepeatRuleRequest(
            repeatType = repeatRule.repeatType,
            repeatInterval = repeatRule.repeatInterval,
            repeatDay = repeatRule.repeatDay,
            startDate = repeatRule.startDate,
            endDate = repeatRule.endDate,
        )
    }
}
