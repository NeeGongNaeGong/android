package com.ssafy.neegongnaegong.data.model.studygroup.response

import java.time.LocalDateTime

data class StudyGroupNoticeDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val userProfileImage: String,
    val writer: Writer,
)
