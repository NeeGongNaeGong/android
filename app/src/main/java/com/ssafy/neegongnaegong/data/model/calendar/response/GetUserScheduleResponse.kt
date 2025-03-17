package com.ssafy.neegongnaegong.data.model.calendar.response

data class GetUserScheduleResponse(
    val year: Int,
    val month: Int,
    val schedules: List<ScheduleResponse>
)
