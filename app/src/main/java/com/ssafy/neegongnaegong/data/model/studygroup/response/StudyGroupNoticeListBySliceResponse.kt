package com.ssafy.neegongnaegong.data.model.studygroup.response

data class StudyGroupNoticeListBySliceResponse(
    val content: List<StudyGroupVoteHistoryResponse>,
    val hasNext: Boolean,
    val cursorId: Long,
)
