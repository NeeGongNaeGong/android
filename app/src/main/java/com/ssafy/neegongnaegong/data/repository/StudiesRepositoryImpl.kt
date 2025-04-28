package com.ssafy.neegongnaegong.data.repository

import com.ssafy.neegongnaegong.data.remote.StudiesApi
import com.ssafy.neegongnaegong.domain.model.Studies
import com.ssafy.neegongnaegong.domain.repository.StudiesRepository
import javax.inject.Inject

class StudiesRepositoryImpl
    @Inject
    constructor(
        private val api: StudiesApi,
    ) : StudiesRepository {
        override suspend fun getStudies(): List<Studies> = api.getStudies()
    }
