package com.ssafy.neegongnaegong.domain.usecase

import com.ssafy.neegongnaegong.domain.model.Studies
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import javax.inject.Inject

class GetStudiesUseCase
    @Inject
    constructor(
        private val repository: StudiesRepository,
    ) {
        suspend operator fun invoke(): List<Studies> = repository.getStudies()
    }
