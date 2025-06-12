package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.StudyInfo
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository

class UpdateStudiesUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(
        studyGroupId: Long,
        studyInfo: StudyInfo,
    ) = studiesRepository.updateStudies(studyGroupId, studyInfo)
}
