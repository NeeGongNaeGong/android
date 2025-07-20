package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.local.database.entity.StudyGroupVoteHistory
import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupVoteHistoryInfoMapper.toEntity
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupVoteHistoryResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.VoteHistoryInfo

internal object StudyGroupVoteHistoryInfoMapper {
    fun StudyGroupVoteHistoryResponse.toEntity(studyGroupId: Long) =
        StudyGroupVoteHistory(
            studyGroupId,
            id,
            title,
            endTime,
            participationMember,
            voted,
        )

    fun List<StudyGroupVoteHistoryResponse>.toEntity(studyGroupId: Long) = map { it.toEntity(studyGroupId) }

    fun StudyGroupVoteHistory.toDomain() =
        VoteHistoryInfo(
            id,
            title,
            endTime,
            participationMember,
            voted,
        )
}
