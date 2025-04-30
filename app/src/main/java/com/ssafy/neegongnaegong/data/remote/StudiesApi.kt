package com.ssafy.neegongnaegong.data.remote

import com.ssafy.neegongnaegong.domain.model.studies.Studies
import retrofit2.http.GET

interface StudiesApi {
    @GET("/studies")
    suspend fun getStudies(): List<Studies>
}
