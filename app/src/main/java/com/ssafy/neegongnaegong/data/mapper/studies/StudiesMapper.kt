package com.ssafy.neegongnaegong.data.mapper.studies

import com.ssafy.neegongnaegong.data.mapper.cursor.CursorMapper.toDomain
import com.ssafy.neegongnaegong.data.model.studies.response.CursorSliceStudiesListResponse
import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage

internal object StudiesMapper {
    fun CursorSliceStudiesListResponse.toDomain() =
        CursorStudiesPage(
            content = content.map { it.toDomain() },
            hasNext = hasNext,
            nextCursor = nextCursor.toDomain(),
        )
}
