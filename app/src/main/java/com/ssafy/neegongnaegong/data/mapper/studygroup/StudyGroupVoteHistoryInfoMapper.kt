package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteHistoryResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo

internal object StudyGroupVoteHistoryInfoMapper {
    fun StudyGroupVoteHistoryResponse.toDomain() =
        VoteHistoryInfo(
            title,
            endTime,
            participationMember,
            voted,
        )

    fun List<StudyGroupVoteHistoryResponse>.toDomain() = map { it.toDomain() }
}
