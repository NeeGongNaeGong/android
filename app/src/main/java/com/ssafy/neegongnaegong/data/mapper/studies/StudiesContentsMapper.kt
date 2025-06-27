package com.ssafy.neegongnaegong.data.mapper.studies

import com.ssafy.neegongnaegong.data.model.studies.response.GetStudiesLatestContentResponse
import com.ssafy.neegongnaegong.data.model.studies.response.StudiesLatestContentResponse
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContent
import com.ssafy.neegongnaegong.domain.model.studies.StudiesLatestContents

object StudiesContentsMapper {
    fun GetStudiesLatestContentResponse.toDomain() =
        StudiesLatestContents(
            latestNotice = noticeSimpleResponse?.toDomain(),
            latestVote = voteSimpleResponse?.toDomain(),
        )

    fun StudiesLatestContentResponse.NoticeResponse.toDomain() =
        StudiesLatestContent.LatestNotice(
            id = id,
            title = title,
            createdAt = createdAt,
        )

    fun StudiesLatestContentResponse.VoteResponse.toDomain() =
        StudiesLatestContent.LatestVote(
            id = id,
            title = title,
            endTime = endTime,
        )
}
