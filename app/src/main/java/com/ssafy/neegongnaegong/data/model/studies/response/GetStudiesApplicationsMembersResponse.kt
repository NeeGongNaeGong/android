package com.ssafy.neegongnaegong.data.model.studies.response

data class GetStudiesApplicationsMembersResponse(
    val content: List<StudiesApplicationsMemberResponse>,
    val hasNext: Boolean,
    val cursorId: Long,
)
