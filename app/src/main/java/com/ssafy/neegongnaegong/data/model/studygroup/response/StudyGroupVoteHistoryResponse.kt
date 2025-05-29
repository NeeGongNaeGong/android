package com.ssafy.neegongnaegong.data.model.studygroup.response

import java.time.LocalDate

data class StudyGroupVoteHistoryResponse(
    val title: String,
    val endTime: LocalDate?,
    val participationMember: Int,
    val voted: Boolean,
)
