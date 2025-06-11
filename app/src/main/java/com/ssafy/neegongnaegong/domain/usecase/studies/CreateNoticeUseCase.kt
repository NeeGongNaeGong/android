package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateNoticeUseCase
    @Inject
    constructor(
        private val studiesRepository: StudiesRepository,
    ) {
        operator fun invoke(
            studyGroupId: Long,
            title: String,
            content: String,
        ): Flow<Unit> {
            return studiesRepository.createNotice(studyGroupId, title, content)
        }
    }
