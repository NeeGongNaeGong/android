package com.ssafy.neegongnaegong.data.model.calendar.request

import com.ssafy.neegongnaegong.domain.model.calendar.DeleteType
import java.time.LocalDate

data class DeletePersonalScheduleRequest(
    val type: DeleteType,
    val date: LocalDate,
)
