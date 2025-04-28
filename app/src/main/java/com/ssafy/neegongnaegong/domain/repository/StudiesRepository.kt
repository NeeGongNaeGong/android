package com.ssafy.neegongnaegong.domain.repository

import com.ssafy.neegongnaegong.domain.model.Studies

interface StudiesRepository {
    suspend fun getStudies(): List<Studies>
}
