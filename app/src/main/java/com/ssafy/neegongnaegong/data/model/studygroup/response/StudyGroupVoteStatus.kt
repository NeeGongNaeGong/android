package com.ssafy.neegongnaegong.data.model.studygroup.response

data class StudyGroupVoteStatus(
    val voteItemName: String,
    val voteItemValue: Long,
    val voteMembers: List<String>,
)
