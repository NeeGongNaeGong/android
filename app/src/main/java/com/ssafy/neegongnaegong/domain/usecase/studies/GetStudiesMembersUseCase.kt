package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.StudiesMember
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow

class GetStudiesMembersUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(studyGroupId: Long): Flow<List<StudiesMember>> =
        studiesRepository.getStudiesMembers(studyGroupId = studyGroupId)
}
