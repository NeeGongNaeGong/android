package com.ssafy.neegongnaegong.domain.model.studygroup

data class StudyGroupVoteStatusInfo(
    val voteItemName: String,
    val voteItemValue: Long,
    val voteMembers: List<String>,
)
