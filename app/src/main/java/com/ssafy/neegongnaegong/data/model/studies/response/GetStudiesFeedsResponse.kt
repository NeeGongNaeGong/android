package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData
import com.ssafy.neegongnaegong.data.model.learningrecord.response.GetLearningRecordResponse

data class GetStudiesFeedsResponse(
    val content: List<GetLearningRecordResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData,
)
