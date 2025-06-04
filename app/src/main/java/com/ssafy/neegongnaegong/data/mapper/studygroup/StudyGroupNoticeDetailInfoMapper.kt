package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeDetailResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.Writer
import com.ssafy.neegongnaegong.domain.model.studygroup.StudyGroupNoticeDetailInfo

internal object StudyGroupNoticeDetailInfoMapper {
    fun Writer.toDomain() =
        com.ssafy.neegongnaegong.domain.model.studygroup.Writer(
            userId,
            name,
            profileImg,
            groupRole,
        )

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
