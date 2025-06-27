package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import javax.inject.Inject

class GetStudiesLatestContentsUseCase
    @Inject
    constructor(
        private val repository: StudiesRepository,
    ) {
        operator fun invoke(studyGroupId: Long) =
            repository.getStudiesLatestContents(
                studyGroupId = studyGroupId,
            )
    }
