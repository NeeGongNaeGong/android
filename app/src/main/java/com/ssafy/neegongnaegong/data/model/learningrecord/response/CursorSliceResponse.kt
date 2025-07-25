package com.ssafy.neegongnaegong.data.model.learningrecord.response

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

data class CursorSliceResponse(
    val content: List<GetLearningRecordResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData,
)
