package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.repository.StudiesRepository

class ExpelStudiesMemberUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(
        studyGroupId: Long,
        userId: Long,
    ) = studiesRepository.expelStudiesMember(studyGroupId = studyGroupId, userId = userId)
}
