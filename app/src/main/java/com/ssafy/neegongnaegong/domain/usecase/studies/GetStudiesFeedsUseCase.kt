package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import javax.inject.Inject

class GetStudiesFeedsUseCase
    @Inject
    constructor(
        private val repository: StudiesRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            cursorValue: String?,
            cursorId: Long?,
            size: Int = 10,
        ) = repository.getStudiesFeeds(
            studyGroupId = studyGroupId,
            cursorValue = cursorValue,
            cursorId = cursorId,
            size = size,
        )
    }
