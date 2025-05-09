package com.ssafy.neegongnaegong.domain.usecase.studies

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import kotlinx.coroutines.flow.Flow

class GetStudiesListUseCase(
    private val studiesRepository: StudiesRepository,
) {
    suspend operator fun invoke(): Flow<List<Studies>> = studiesRepository.getStudiesList()
}
