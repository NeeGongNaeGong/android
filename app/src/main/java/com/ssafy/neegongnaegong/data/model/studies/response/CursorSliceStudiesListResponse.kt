package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

data class CursorSliceStudiesListResponse(
    val content: List<StudiesResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData,
)
