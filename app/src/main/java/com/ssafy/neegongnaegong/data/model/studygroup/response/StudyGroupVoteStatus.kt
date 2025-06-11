package com.ssafy.neegongnaegong.data.model.studygroup.response

data class StudyGroupVoteStatus(
    val voteItemId: Long,
    val voteItemName: String,
    val voteItemValue: Long,
    val votedMembers: List<VotedMemberStatus> = emptyList(),
) {
    data class VotedMemberStatus(val id: Long, val username: String, val profileImg: String)
}
