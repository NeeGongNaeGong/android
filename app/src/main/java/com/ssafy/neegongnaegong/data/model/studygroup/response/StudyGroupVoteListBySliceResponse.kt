package com.ssafy.neegongnaegong.data.model.studygroup.response

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

data class StudyGroupVoteListBySliceResponse(
    val content: List<StudyGroupVoteHistoryResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData?,
)
