package com.ssafy.neegongnaegong.data.model.calendar.request

import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatType
import java.time.LocalDate

data class CreateRepeatRuleRequest(
    val repeatType: RepeatType,
    val repeatInterval: Int,
    val repeatDay: Int,
    val endDate: LocalDate? = null,
) {
    companion object {
        fun fromDomain(repeatRule: RepeatRuleInfo) = CreateRepeatRuleRequest(
            repeatType = repeatRule.repeatType,
            repeatInterval = repeatRule.repeatInterval,
            repeatDay = repeatRule.repeatDay,
            endDate = repeatRule.endDate,
        )
    }
}
