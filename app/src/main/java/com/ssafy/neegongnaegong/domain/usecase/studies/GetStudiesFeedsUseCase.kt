package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetStudiesFeedsUseCase
    @Inject
    constructor(
        private val repository: StudiesRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            cursorCreatedAt: LocalDateTime?,
            cursorId: Long?,
            size: Int = 10,
        ) = repository.getStudiesFeeds(
            studyGroupId = studyGroupId,
            cursorCreatedAt = cursorCreatedAt,
            cursorId = cursorId,
            size = size,
        )
    }
