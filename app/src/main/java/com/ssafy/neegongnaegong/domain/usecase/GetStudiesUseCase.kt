package com.ssafy.neegongnaegong.domain.usecase

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository

class GetStudiesUseCase(
    private val repository: StudiesRepository,
) {
    suspend operator fun invoke(): List<Studies> = repository.getStudies()
}
