package com.ssafy.neegongnaegong.data.model.studies.request

import java.time.LocalDateTime

data class GetStudiesListRequest(
    val cursorCreatedAt: LocalDateTime? = null,
    val cursorId: Long? = null,
    val size: Int = 10
)
