package com.ssafy.neegongnaegong.data.model.studies.request

data class GetStudiesListRequest(
    val cursorCreatedAt: String? = null,
    val cursorId: Long? = null,
    val size: Int = 10,
)
