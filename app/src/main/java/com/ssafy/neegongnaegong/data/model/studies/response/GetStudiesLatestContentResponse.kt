package com.ssafy.neegongnaegong.data.model.studies.response

data class GetStudiesLatestContentResponse(
    val noticeSimpleResponse: StudiesLatestContentResponse.NoticeResponse?,
    val voteSimpleResponse: StudiesLatestContentResponse.VoteResponse?,
)
