package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.model.studygroup.response.StudyGroupNoticeHistoryResponse
import com.ssafy.neegongnaegong.data.model.studygroup.response.Writer
import com.ssafy.neegongnaegong.domain.model.studygroup.NoticeHistoryInfo

internal object StudyGroupNoticeHistoryInfoMapper {
    fun Writer.toDomain() =
        com.ssafy.neegongnaegong.domain.model.studygroup.Writer(
            userId,
            name,
            profileImg,
            groupRole,
        )

    fun StudyGroupNoticeHistoryResponse.toDomain() =
        NoticeHistoryInfo(
            id,
            cursorId,
            title,
            contentPreview,
            createdAt,
            modifiedAt,
            writer.toDomain(),
        )

    fun List<StudyGroupNoticeHistoryResponse>.toDomain() = map { it.toDomain() }
}
