package com.ssafy.neegongnaegong.data.model.studies.response

data class GetStudiesLatestContentsReadStatusResponse(
    val lastNoticeChecked: Boolean,
    val lastVoteChecked: Boolean,
)
