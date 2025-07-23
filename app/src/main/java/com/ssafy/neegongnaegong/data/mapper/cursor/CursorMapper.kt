package com.ssafy.neegongnaegong.data.mapper.cursor

import com.ssafy.neegongnaegong.data.model.cursor.NextCursorData
import com.ssafy.neegongnaegong.domain.model.cursor.NextCursorDomain

internal object CursorMapper {
    fun NextCursorData.toDomain() =
        NextCursorDomain(
            cursorValue = cursorValue,
            cursorId = cursorId,
            first = first,
        )
}
