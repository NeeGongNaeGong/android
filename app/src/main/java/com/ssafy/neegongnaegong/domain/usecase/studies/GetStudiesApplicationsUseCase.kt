package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository

class GetStudiesApplicationsUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(
        studyGroupId: Long,
        cursorId: Long?,
        size: Int,
    ) = studiesRepository.getStudiesApplications(
        studyGroupId = studyGroupId,
        cursorId = cursorId,
        size = size,
    )
}
