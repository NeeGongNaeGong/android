package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetStudiesWeeklyRankingsUseCase
    @Inject
    constructor(
        private val repository: StudiesRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            cursorValue: String?,
            cursorId: Long?,
            firstPageRequestedAt: LocalDateTime?,
            size: Int = 10,
        ) = repository.getStudiesWeeklyRankings(
            studyGroupId = studyGroupId,
            cursorValue = cursorValue,
            cursorId = cursorId,
            firstPageRequestedAt = firstPageRequestedAt,
            size = size,
        )
    }
