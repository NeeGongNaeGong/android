package com.ssafy.neegongnaegong.domain.model.learning

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class LearningRecord(
    val id: Long,
    val title: String,
    val content: String,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
    val tags: List<Tag>,
) {
    companion object {
        private val formatter = DateTimeFormatter.ISO_DATE_TIME

        fun default(): LearningRecord =
            LearningRecord(
                id = 0,
                title = "",
                content = "",
                startAt =
                    LocalDateTime.parse(
                        "2025-02-02T06:33:02.856Z",
                        formatter.withZone(
                            ZoneOffset.UTC,
                        ),
                    ),
                endAt =
                    LocalDateTime.parse(
                        "2025-02-02T08:33:02.856Z",
                        formatter.withZone(ZoneOffset.UTC),
                    ),
                tags = emptyList(),
            )
    }
}

