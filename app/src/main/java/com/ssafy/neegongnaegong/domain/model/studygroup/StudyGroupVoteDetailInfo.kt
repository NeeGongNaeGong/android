package com.ssafy.neegongnaegong.domain.model.studygroup

data class StudyGroupVoteDetailInfo(
    val userName: String,
    val progressTime: String,
    val voteTitle: String,
    val voteOptions: List<String>,
    val voteItems: List<StudyGroupVoteStatusInfo>,
)
