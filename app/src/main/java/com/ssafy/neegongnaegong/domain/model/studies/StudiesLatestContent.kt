package com.ssafy.neegongnaegong.domain.model.studies

import java.time.LocalDateTime

sealed interface StudiesLatestContent {
    val id: Long
    val title: String

    data class LatestNotice(
        override val id: Long,
        override val title: String,
        val createdAt: LocalDateTime,
    ) : StudiesLatestContent

    data class LatestVote(
        override val id: Long,
        override val title: String,
        val endTime: LocalDateTime,
    ) : StudiesLatestContent
}
