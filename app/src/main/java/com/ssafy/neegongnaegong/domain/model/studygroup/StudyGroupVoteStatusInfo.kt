package com.ssafy.neegongnaegong.domain.model.studygroup

data class StudyGroupVoteStatusInfo(
    val voteItemId: Long,
    val voteItemName: String,
    val voteItemValue: Long,
    val votedMembers: List<VotedMemberInfo>,
) {
    data class VotedMemberInfo(val id: Long, val username: String, val profileImg: String)
}
