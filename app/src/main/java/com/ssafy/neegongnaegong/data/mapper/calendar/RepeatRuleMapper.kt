package com.ssafy.neegongnaegong.data.mapper.calendar

import com.ssafy.neegongnaegong.data.model.calendar.request.CreateRepeatRuleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdateRepeatRuleRequest
import com.ssafy.neegongnaegong.data.model.calendar.response.RepeatRuleResponse
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRule
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo

internal object RepeatRuleMapper {
    fun RepeatRuleInfo.toCreateRequest() = CreateRepeatRuleRequest(
        repeatType = repeatType,
        repeatInterval = repeatInterval,
        repeatDay = repeatDay,
        endDate = endDate,
    )

    fun RepeatRuleInfo.toUpdateRequest() = UpdateRepeatRuleRequest(
        repeatType = repeatType,
        repeatInterval = repeatInterval,
        repeatDay = repeatDay,
        endDate = endDate,
    )

    fun RepeatRuleResponse.toDomain() = RepeatRule(
        id = id,
        info = RepeatRuleInfo(
            repeatType = repeatType,
            repeatInterval = repeatInterval,
            repeatDay = repeatDay,
            endDate = endDate
        )
    )
}
