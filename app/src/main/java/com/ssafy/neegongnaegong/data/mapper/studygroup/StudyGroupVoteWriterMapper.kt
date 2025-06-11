package com.ssafy.neegongnaegong.data.mapper.studygroup

import com.ssafy.neegongnaegong.data.model.studygroup.response.Writer

internal object StudyGroupVoteWriterMapper {
    fun Writer.toDomain() =
        com.ssafy.neegongnaegong.domain.model.studygroup.Writer(
            userId,
            name,
            profileImg,
            groupRole,
        )
}
