package com.ssafy.neegongnaegong.data.model.studygroup.response

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

data class MemberWeeklyStudyContentBySliceResponse(
    val content: List<StudyContentResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData,
)
