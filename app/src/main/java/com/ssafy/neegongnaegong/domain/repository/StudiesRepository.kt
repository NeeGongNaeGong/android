package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.studies.Studies

interface StudiesRepository {
    suspend fun getStudies(): List<Studies>
}
