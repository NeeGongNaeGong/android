package com.ssafy.neegongnaegong.domain.model.studygroup

data class StudyGroupVoteDetailInfo(
    val userName: String,
    val userProfileImg: String,
    val progressTime: String,
    val voteTitle: String,
    val voteOptions: List<String>,
    val voteItems: List<StudyGroupVoteStatusInfo>,
    val voteValues: List<VoteValue>,
) {
    data class VoteValue(val voteItemId: Long, val voteItemName: String)
}
