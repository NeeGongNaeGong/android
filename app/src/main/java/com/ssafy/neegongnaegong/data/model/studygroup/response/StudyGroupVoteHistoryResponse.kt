package com.ssafy.neegongnaegong.data.model.studygroup.response

import java.time.LocalDateTime

data class StudyGroupVoteHistoryResponse(
    val id: Long,
    val title: String,
    val endTime: LocalDateTime?,
    val participationMember: Int,
    val voted: Boolean,
)
