package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDateTime

data class NoticeHistoryInfo(
    val id: Long,
    val cursorId: Long,
    val title: String,
    val contentPreview: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val writer: Writer,
)

data class Writer(
    val userId: Long,
    val name: String,
    val profileImg: String,
    val groupRole: String,
)
