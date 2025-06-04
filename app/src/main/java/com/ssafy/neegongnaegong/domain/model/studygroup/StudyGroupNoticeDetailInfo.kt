package com.ssafy.neegongnaegong.domain.model.studygroup

import java.time.LocalDateTime

data class StudyGroupNoticeDetailInfo(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
    val userProfileImage: String?,
    val writer: Writer,
)
