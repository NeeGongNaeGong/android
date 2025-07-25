package com.ssafy.neegongnaegong.data.model.studygroup.response

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

data class StudyGroupNoticeListBySliceResponse(
    val content: List<StudyGroupNoticeHistoryResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData,
)
