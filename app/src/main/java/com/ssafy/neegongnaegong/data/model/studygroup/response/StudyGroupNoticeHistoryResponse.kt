package com.ssafy.neegongnaegong.data.model.studygroup.response

import java.time.LocalDateTime

data class StudyGroupNoticeHistoryResponse(
    val id: Long,
    val cursorId: Long,
    val title: String,
    val contentPreview: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val writer: Writer,
)
