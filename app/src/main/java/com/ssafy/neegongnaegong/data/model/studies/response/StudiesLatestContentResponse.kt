package com.ssafy.neegongnaegong.data.model.studies.response

import java.time.LocalDateTime

sealed interface StudiesLatestContentResponse {
    val id: Long
    val title: String

    data class NoticeResponse(
        override val id: Long,
        override val title: String,
        val createdAt: LocalDateTime,
    ) : StudiesLatestContentResponse

    data class VoteResponse(
        override val id: Long,
        override val title: String,
        val endTime: LocalDateTime,
    ) : StudiesLatestContentResponse
}
