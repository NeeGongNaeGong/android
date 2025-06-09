package com.ssafy.neegongnaegong.data.model.studygroup.response

data class StudyGroupVoteDetailResponse(
    val userName: String,
    val userProfileImg: String,
    val progressTime: String,
    val voteTitle: String,
    val voteOptions: List<String>,
    val voteItems: List<StudyGroupVoteStatus>,
    val voteValues: List<VoteValue>,
) {
    data class VoteValue(val voteItemId: Long, val voteItemName: String)
}
