package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.mapper.studygroup.StudyGroupVoteWriterMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeDetailResponse
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeDetailInfo

internal object StudyGroupNoticeDetailInfoMapper {
    fun StudyGroupNoticeDetailResponse.toDomain() =
        StudyGroupNoticeDetailInfo(
            id,
            title,
            content,
            createdAt,
            modifiedAt,
            userProfileImage,
            writer.toDomain(),
        )
}
