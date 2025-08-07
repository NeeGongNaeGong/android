package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.CursorStudiesPage
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetStudiesSearchUseCase
    @Inject
    constructor(
        private val repository: StudiesRepository,
    ) {
        suspend operator fun invoke(searchKeyword: String): Flow<CursorStudiesPage> =
            repository.getStudiesSearch(
                keyword = searchKeyword,
                sort = null,
                categoryIds = null,
                cursorValue = null,
                cursorId = null,
                size = 10,
            )
    }
