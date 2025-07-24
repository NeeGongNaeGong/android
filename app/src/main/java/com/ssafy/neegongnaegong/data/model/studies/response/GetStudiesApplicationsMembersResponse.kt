package com.ssafy.neegongnaegong.data.model.studies.response

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData

data class GetStudiesApplicationsMembersResponse(
    val content: List<StudiesApplicationsMemberResponse>,
    val hasNext: Boolean,
    val nextCursor: NextCursorData,
)
