package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository

class CancelApplicationsStudiesUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(studiesId: Long) = studiesRepository.cancelApplicationsStudies(studiesId)
}
