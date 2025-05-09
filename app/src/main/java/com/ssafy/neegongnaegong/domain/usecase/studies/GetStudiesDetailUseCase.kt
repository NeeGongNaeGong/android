package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository

class GetStudiesDetailUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(studyGroupId: Long) =
        studiesRepository.getStudiesDetail(
            studyGroupId,
        )
}
