package com.ssafy.neegongnaegong.data.mapper.calendar

import com.ssafy.neegongnaegong.data.mapper.calendar.RepeatRuleMapper.toCreateRequest
import com.ssafy.neegongnaegong.data.mapper.calendar.RepeatRuleMapper.toDomain
import com.ssafy.neegongnaegong.data.mapper.calendar.RepeatRuleMapper.toUpdateRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.CreatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.request.UpdatePersonalScheduleRequest
import com.ssafy.neegongnaegong.data.model.calendar.response.CreatePersonalScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.ScheduleResponse
import com.ssafy.neegongnaegong.data.model.calendar.response.UpdatePersonalScheduleResponse
import com.ssafy.neegongnaegong.domain.model.calendar.RepeatRuleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.Schedule
import com.ssafy.neegongnaegong.domain.model.calendar.ScheduleInfo
import com.ssafy.neegongnaegong.domain.model.calendar.UpdateType
import java.time.LocalDate

internal object ScheduleMapper {

    fun ScheduleInfo.toCreateRequest(repeatRule: RepeatRuleInfo? = null) =
        CreatePersonalScheduleRequest(
            title = title,
            content = content,
            startAt = startAt,
            endAt = endAt,
            location = location,
            repeatRule = repeatRule?.toCreateRequest()
        )

    fun ScheduleInfo.toUpdateRequest(
        type: UpdateType,
        date: LocalDate,
        repeatRule: RepeatRuleInfo?
    ) = UpdatePersonalScheduleRequest(
        type = type,
        date = date,
        title = title,
        content = content,
        startAt = startAt,
        endAt = endAt,
        location = location,
        repeatRule = repeatRule?.let { repeatRule.toUpdateRequest() }
    )

    fun CreatePersonalScheduleResponse.toDomain() = Schedule(
        type = type,
        id = id,
        info = ScheduleInfo(
            title = title,
            content = content,
            startAt = startAt,
            endAt = endAt,
//            isAllDay = endDate.second == 59, // TODO: 추후 수정
            location = location,
            repeatRule = repeatRule?.toDomain(),
        ),
    )

    fun UpdatePersonalScheduleResponse.toDomain() = Schedule(
        type = type,
        id = id,
        info = ScheduleInfo(
            title = title,
            content = content,
            startAt = startAt,
            endAt = endAt,
//            isAllDay = endDate.second == 59, // TODO: 추후 수정
            location = location,
            repeatRule = repeatRule?.toDomain()
        ),
    )

    fun ScheduleResponse.toDomain() = Schedule(
        type = type,
        id = id,
        info = ScheduleInfo(
            title = title,
            content = content,
            startAt = startAt,
            endAt = endAt,
//            isAllDay = endAt.second == 59, // TODO: 추후 수정
            location = location,
            repeatRule = repeatRule?.toDomain()
        ),
    )
}
