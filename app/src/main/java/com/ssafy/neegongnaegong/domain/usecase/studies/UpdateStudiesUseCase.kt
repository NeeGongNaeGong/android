package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository

class UpdateStudiesUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(
        studyGroupId: Long,
        studies: Studies,
    ) = studiesRepository.updateStudies(studyGroupId, studies)
}
