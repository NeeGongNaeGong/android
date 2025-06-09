package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupVoteDetailInfoMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteStatus
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteStatusInfo

internal object StudyGroupVoteDetailInfoMapper {
    fun StudyGroupVoteStatus.VotedMemberStatus.toDomain() =
        StudyGroupVoteStatusInfo.VotedMemberInfo(
            id = id,
            username = username,
            profileImg = profileImg,
        )

    private fun List<StudyGroupVoteStatus.VotedMemberStatus>.toVoteMemberStatusDomain() = map { it.toDomain() }

    fun StudyGroupVoteStatus.toDomain() =
        StudyGroupVoteStatusInfo(
            voteItemId = voteItemId,
            voteItemName = voteItemName,
            voteItemValue = voteItemValue,
            votedMembers = votedMembers.toVoteMemberStatusDomain(),
        )

    fun List<StudyGroupVoteStatus>.toDomain() = map { it.toDomain() }

    fun StudyGroupVoteDetailResponse.VoteValue.toDomain() =
        StudyGroupVoteDetailInfo.VoteValue(
            voteItemId = voteItemId,
            voteItemName = voteItemName,
        )

    private fun List<StudyGroupVoteDetailResponse.VoteValue>.toVoteValueDomain() = map { it.toDomain() }

    fun StudyGroupVoteDetailResponse.toDomain() =
        StudyGroupVoteDetailInfo(
            userName = userName,
            userProfileImg = userProfileImg,
            progressTime = progressTime,
            voteTitle = voteTitle,
            voteOptions = voteOptions,
            voteItems = voteItems.toDomain(),
            voteValues = voteValues.toVoteValueDomain(),
        )
}
