package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class GetStudiesListUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(
        cursorCreatedAt: LocalDateTime? = null,
        cursorId: Long? = null,
        size: Int = 10,
    ): Flow<CursorStudiesPage> =
        studiesRepository.getStudiesList(
            cursorCreatedAt = cursorCreatedAt,
            cursorId = cursorId,
            size = size,
        )
}
