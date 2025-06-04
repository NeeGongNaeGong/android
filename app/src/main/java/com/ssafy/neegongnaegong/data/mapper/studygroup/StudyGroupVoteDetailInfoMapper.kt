package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteStatus
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteDetailInfo
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupVoteStatusInfo

internal object StudyGroupVoteDetailInfoMapper {
    fun StudyGroupVoteStatus.toDomain() =
        StudyGroupVoteStatusInfo(
            voteItemName = voteItemName,
            voteItemValue = voteItemValue,
            voteMembers = voteMembers,
        )

    fun List<StudyGroupVoteStatus>.toDomain() = map { it.toDomain() }

    fun StudyGroupVoteDetailResponse.toDomain() =
        StudyGroupVoteDetailInfo(
            userName = userName,
            progressTime = progressTime,
            voteTitle = voteTitle,
            voteOptions = voteOptions,
            voteItems = voteItems.toDomain(),
        )
}
