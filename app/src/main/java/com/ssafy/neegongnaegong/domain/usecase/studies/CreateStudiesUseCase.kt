package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow

class CreateStudiesUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(studyInfo: StudyInfo): Flow<Unit> = studiesRepository.createStudies(studyInfo)
}
